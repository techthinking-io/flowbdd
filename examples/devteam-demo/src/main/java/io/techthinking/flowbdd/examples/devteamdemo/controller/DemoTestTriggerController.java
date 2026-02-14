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
package io.techthinking.flowbdd.examples.devteamdemo.controller;

import io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorTest;
import io.techthinking.flowbdd.examples.devteam.model.ProductivityBoost;
import io.techthinking.flowbdd.report.report.model.DataReportIndex;
import io.techthinking.flowbddserver.api.RunRequest;
import io.techthinking.flowbddserver.core.TestRunService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/demo")
public class DemoTestTriggerController {

    private final TestRunService testRunService;

    public DemoTestTriggerController(TestRunService testRunService) {
        this.testRunService = testRunService;
    }

    @PostMapping("/ping")
    public String ping() {
        DevTeamSimulatorTest test = new DevTeamSimulatorTest();
        ProductivityBoost boost = new ProductivityBoost("developer", 10, "message");
        System.out.println("ping this shows that we have access to src and test. Class instances test: " + test + " boost: " + boost);
        return "pong";
    }

    @PostMapping("/run-devteam-tests")
    public DataReportIndex runDevTeamTests() {
        RunRequest request = new RunRequest();
        // Assuming we want to run all tests in this package
        request.setTags(List.of("devteam"));
        // Or run a specific class if tags are not used
        // request.setClassName("DevTeamSimulatorTest");
        return testRunService.runTests(request);
    }
    
    @PostMapping("/run-custom")
    public DataReportIndex runCustom(@RequestBody RunRequest request) {
        return testRunService.runTests(request);
    }
}
