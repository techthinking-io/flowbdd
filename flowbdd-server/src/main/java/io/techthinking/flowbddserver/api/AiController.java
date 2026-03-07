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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final ReportController reportController;
    private final RestTemplate restTemplate;

    @Value("${openai.api.key:}")
    private String apiKey;

    @Autowired
    public AiController(ReportController reportController) {
        this.reportController = reportController;
        this.restTemplate = new RestTemplate();
    }

    public AiController(ReportController reportController, RestTemplate restTemplate) {
        this.reportController = reportController;
        this.restTemplate = restTemplate;
    }

    @GetMapping(path = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> ping() {
        return Map.of(
            "message", "pong from Flow BDD Server - /ai",
            "timestamp", LocalDateTime.now().toString()
        );
    }

    @PostMapping("/ask")
    public Map<String, Object> ask(@RequestBody AiRequest request) {
        if (apiKey == null || apiKey.isEmpty()) {
            return Map.of("error", "OpenAI API key not configured. Please set openai.api.key in application.properties or as an environment variable.");
        }
        TestSuite testSuite = reportController.getTestSuiteByClass(request.getClassName());
        String data = FlowBddConfig.isAiOptimized() 
            ? TestSuiteMarkdownFactory.toMarkdown(testSuite) 
            : serializeTestSuite(testSuite);

        String prompt = String.format(
            FlowBddConfig.getAiPrompt(),
            request.getQuestion(),
            data
        );

        return callOpenAi(prompt);
    }

    private String serializeTestSuite(TestSuite testSuite) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(testSuite);
        } catch (Exception e) {
            return "Error serializing test suite";
        }
    }

    private Map<String, Object> callOpenAi(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", List.of(
            Map.of("role", "system", "content", "You are an assistant that helps developers understand their BDD test results."),
            Map.of("role", "user", "content", prompt)
        ));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                return Map.of("answer", message.get("content"));
            }
            return Map.of("error", "Empty response from OpenAI");
        } catch (Exception e) {
            return Map.of("error", "Failed to call OpenAI: " + e.getMessage());
        }
    }

    public static class AiRequest {
        private String className;
        private String question;

        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }
        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
    }
}
