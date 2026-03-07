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

import io.techthinking.flowbdd.report.config.FlowBddConfig;
import io.techthinking.flowbdd.report.report.model.TestSuite;
import io.techthinking.flowbdd.report.report.model.TestSuiteMarkdownFactory;
import io.techthinking.flowbdd.report.report.model.builders.TestSuiteBuilder;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void tearDown() {
        System.clearProperty("flowbdd.ai.optimized");
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
    void ask_callsOpenAiWithMarkdownWhenOptimized() throws Exception {
        System.setProperty("flowbdd.ai.optimized", "true");
        ReflectionTestUtils.setField(aiController, "apiKey", "test-api-key");
        String className = "io.techthinking.MarkdownTest";
        String question = "Explain these results";
        String requestBody = String.format("{\"className\":\"%s\", \"question\":\"%s\"}", className, question);

        TestSuite testSuite = TestSuiteBuilder.aTestSuite()
                .withClassName(className)
                .withTitle("Markdown Test Suite")
                .build();

        when(reportController.getTestSuiteByClass(className)).thenReturn(testSuite);

        String expectedMarkdown = TestSuiteMarkdownFactory.toMarkdown(testSuite);
        String expectedPrompt = String.format(FlowBddConfig.getAiPrompt(), question, expectedMarkdown);

        mockServer.expect(requestTo("https://api.openai.com/v1/chat/completions"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(request -> {
                    String body = request.getBody().toString();
                    // Markdown might have escaped newlines in JSON body
                    String normalizedBody = body.replace("\\n", "\n");
                    if (!normalizedBody.contains(expectedMarkdown)) {
                        throw new AssertionError("Request body does not contain expected markdown");
                    }
                })
                .andRespond(withSuccess("{\"choices\":[{\"message\":{\"role\":\"assistant\",\"content\":\"OK\"}}]}", MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/api/ai/ask")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());

        mockServer.verify();
    }
}
