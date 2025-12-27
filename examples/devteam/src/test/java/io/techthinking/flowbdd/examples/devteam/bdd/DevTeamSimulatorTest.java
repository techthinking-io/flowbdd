/*
 * Flow BDD - The productive way to test.
 * Copyright (C)  2025  James Bayliss
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

import com.fasterxml.jackson.core.JsonProcessingException;
import io.techthinking.flowbdd.examples.devteam.model.ProductivityBoost;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.techthinking.flowbdd.examples.devteam.model.TechDept;
import io.techthinking.flowbdd.report.junit5.results.extension.FlowBdd;
import io.techthinking.flowbdd.report.junit5.test.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static io.techthinking.flowbdd.report.mermaid.MessageBuilder.aMessage;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(FlowBdd.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DevTeamSimulatorTest extends BaseTest {

    @Override
    public void doc() {
        featureNotes("Dev Team Simulator: Verify developer actions endpoints – the world's most advanced team simulator... that only masters drinking coffee and accumulating tech debt, with predetermined biased outputs ☕🚫");
    }

    @Autowired
    private TestRestTemplate template;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private ResponseEntity<String> response;
    private ProductivityBoost productivityBoostResponse;
    private TechDept techDeptResponse;

    private static final String USER = "User";
    private static final String DEV_TEAM_SIMULATOR = "DevTeamSimulator";

    @BeforeEach
    void setupDoc() {
        sequenceDiagram()
            .addActor(USER)
            .addParticipant(DEV_TEAM_SIMULATOR);

        response = null;
        productivityBoostResponse = null;
        techDeptResponse = null;
    }

    /**
     * Potentially next steps
     * givenDeveloper("Alice");
     * whenDeveloperDrinksCoffee();
     * thenDeveloperGetsPerformanceBoost();
     */
    @Test
    void developerDrinksCoffee_getsPerformanceBoost() throws Exception {
        whenDeveloperDrinksCoffee();
        thenDeveloperGetsPerformanceBoost();
    }

    @Test
    void developerDoesNoTesting_getsTechDept() throws Exception {
        whenDeveloperDoesNoTesting();
        thenDeveloperGetsTechDept();
    }

    private void whenDeveloperDrinksCoffee() throws JsonProcessingException {
        Object request = Collections.emptyMap();
        response = template.postForEntity("/dev/Alice/drinks-coffee", request, String.class);
        productivityBoostResponse = productivityBoost(response.getBody());
        generateSequenceDiagram("/dev/Alice/drinks-coffee");
    }

    private void whenDeveloperDoesNoTesting() throws JsonProcessingException {
        Object request = Collections.emptyMap();
        response = template.postForEntity("/dev/Bob/does-no-testing", request, String.class);
        techDeptResponse = techDept(response.getBody());
        generateSequenceDiagram("/dev/Bob/does-no-testing");
    }

    private ProductivityBoost productivityBoost(String text) throws JsonProcessingException {
        return MAPPER.readValue(text, ProductivityBoost.class);
    }

    private TechDept techDept(String text) throws JsonProcessingException {
        return MAPPER.readValue(text, TechDept.class);
    }

    private void generateSequenceDiagram(String url) {
        sequenceDiagram().add(aMessage().from(USER).to(DEV_TEAM_SIMULATOR).text(url));
        sequenceDiagram().add(aMessage().from(DEV_TEAM_SIMULATOR).to(USER).text(response.getBody() + " [" + response.getStatusCode().value() + "]"));
    }

    private void thenDeveloperGetsPerformanceBoost() {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(productivityBoostResponse.getDeveloper()).isEqualTo("Alice");
        assertThat(productivityBoostResponse.getBoost()).isEqualTo(40);
        assertThat(productivityBoostResponse.getMessage()).isEqualTo("1.21 Gigawatts of caffeine! Alice is seeing some serious productivity.");
    }

    private void thenDeveloperGetsTechDept() {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(techDeptResponse.getDeveloper()).isEqualTo("Bob");
        assertThat(techDeptResponse.getDebt()).isEqualTo(10);
        assertThat(techDeptResponse.getMessage()).isEqualTo("Bob tonight, we dine in Technical Debt! For tomorrow, we push to production");
    }
}
