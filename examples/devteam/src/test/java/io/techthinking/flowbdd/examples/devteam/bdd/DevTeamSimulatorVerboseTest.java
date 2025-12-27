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

package io.techthinking.flowbdd.examples.devteam.bdd;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.techthinking.flowbdd.report.junit5.results.extension.FlowBdd;
import io.techthinking.flowbdd.report.junit5.test.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static io.techthinking.flowbdd.report.mermaid.MessageBuilder.aMessage;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Example of verbose testing, for just 2 tests it has less code (no sequence diagram).
 * <p>
 * If you write more tests, it would become a maintenance issue.
 */
@ExtendWith(FlowBdd.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DevTeamSimulatorVerboseTest extends BaseTest {

    @Override
    public void doc() {
        featureNotes("Dev Team Simulator: Verify developer actions endpoints – the world's most advanced team simulator... that only masters drinking coffee and accumulating tech debt, with predetermined biased outputs ☕🚫");
    }

    @Autowired
    private TestRestTemplate template;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @BeforeEach
    void setupDoc() {
    }

    @Test
    void developerDrinksCoffee_getPerformanceBoost() throws Exception {
        // when
        ResponseEntity<String> response = template.exchange(
            "/dev/Alice/drinks-coffee",
            HttpMethod.POST,
            null,
            String.class
        );

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        JsonNode json = MAPPER.readTree(response.getBody());
        assertThat(json.get("developer").asText()).isEqualTo("Alice");
        assertThat(json.get("boost").asInt()).isEqualTo(40);
        assertThat(json.get("message").asText()).contains("1.21 Gigawatts of caffeine! Alice is seeing some serious productivity.");
    }

    @Test
    void developerDoesNoTesting_getsTechDept() throws Exception {
        // when
        ResponseEntity<String> response = template.exchange(
            "/dev/Bob/does-no-testing",
            HttpMethod.POST,
            null,
            String.class
        );

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        JsonNode json = MAPPER.readTree(response.getBody());
        assertThat(json.get("developer").asText()).isEqualTo("Bob");
        assertThat(json.get("debt").asInt()).isEqualTo(10);
        assertThat(json.get("message").asText()).contains("Bob tonight, we dine in Technical Debt! For tomorrow, we push to production");
    }
}
