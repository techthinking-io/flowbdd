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

import io.techthinking.flowbdd.report.report.model.DataReportIndex;
import io.techthinking.flowbddserver.FlowBddServerApplication;
import io.techthinking.flowbddserver.api.RunRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = FlowBddServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FlowBddServerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Disabled("Need to share the jimfs/in memory file system")
    // @Test
    void canRunDevTeamTestViaApi() {
        RunRequest request = new RunRequest();
        request.setClassName("io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorTest");
        request.setMethodName("developerDrinksCoffee_getsPerformanceBoost");
        request.setRerunCount(1);

        ResponseEntity<DataReportIndex> response = restTemplate.postForEntity("/api/tests/run", request, DataReportIndex.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        DataReportIndex result = response.getBody();
        assertThat(result).isNotNull();
        assertThat(result.getSummary().getTests()).isEqualTo(1);
        assertThat(result.getSummary().getPassed()).isEqualTo(1);
    }

    @Disabled("Need to share the jimfs/in memory file system")
    // @Test
    void statusIsIdleInitially() {
        ResponseEntity<DataReportIndex> response = restTemplate.getForEntity("/api/tests/status", DataReportIndex.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        DataReportIndex result = response.getBody();
        // It returns null when no tests run based on TestRunService.getLastRun()
        // assertThat(result).isNull();
    }
}
