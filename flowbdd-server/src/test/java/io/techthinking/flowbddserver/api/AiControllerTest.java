/*
 * Flow BDD - The most productive way to test.
 * Copyright (C) 2021-2025 James Bayliss.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.techthinking.flowbddserver.api;

import io.techthinking.flowbdd.report.report.model.TestSuite;
import io.techthinking.flowbdd.report.report.model.builders.TestSuiteBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AiController aiController;

    @MockBean
    private ReportController reportController;

    private MockRestServiceServer mockServer;
    private final RestTemplate restTemplate = new RestTemplate();

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        ReflectionTestUtils.setField(aiController, "restTemplate", restTemplate);
    }

    @Test
    void ask_returnsErrorWhenApiKeyMissing() throws Exception {
        ReflectionTestUtils.setField(aiController, "apiKey", "");
        String requestBody = "{\"className\":\"some.class.Name\", \"question\":\"What is this?\"}";

        mockMvc.perform(post("/api/ai/ask")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("OpenAI API key not configured. Please set openai.api.key in application.properties or as an environment variable."));
    }

    @Test
    void ask_callsOpenAiAndReturnsAnswer() throws Exception {
        ReflectionTestUtils.setField(aiController, "apiKey", "test-api-key");
        String className = "io.techthinking.ExampleTest";
        String question = "Explain these results";
        String requestBody = String.format("{\"className\":\"%s\", \"question\":\"%s\"}", className, question);

        TestSuite testSuite = TestSuiteBuilder.aTestSuite()
                .withClassName(className)
                .withTitle("Example Test Suite")
                .build();

        when(reportController.getTestSuiteByClass(className)).thenReturn(testSuite);

        String openAiResponse = "{" +
                "  \"choices\": [" +
                "    {" +
                "      \"message\": {" +
                "        \"role\": \"assistant\"," +
                "        \"content\": \"The test suite passed successfully.\"" +
                "      }" +
                "    }" +
                "  ]" +
                "}";

        mockServer.expect(requestTo("https://api.openai.com/v1/chat/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(openAiResponse, MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/api/ai/ask")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value("The test suite passed successfully."));

        mockServer.verify();
    }
}
