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
import io.techthinking.flowbdd.report.junit5.launcher.TestLauncher;
import io.techthinking.flowbdd.report.report.model.DataReportIndex;
import io.techthinking.flowbddserver.api.RunRequest;
import io.techthinking.flowbddserver.core.TestRunService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
//import io.techthinking.flowbdd.examples.devteamdemo.DemoTest;

@RestController
@RequestMapping("/api/demo")
public class DemoTestTriggerController {

    private final TestRunService testRunService;
    // This is brittle. Doesn't seem to be an elegant solution.
    // private static final Class<?> PASSING_CLASS_UNDER_TEST = DemoTest.class;

    public DemoTestTriggerController(TestRunService testRunService) {
        this.testRunService = testRunService;
    }

    @GetMapping(path = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> ping() {
        DevTeamSimulatorTest test = new DevTeamSimulatorTest();
        ProductivityBoost boost = new ProductivityBoost("developer", 10, "message");
        System.out.println("ping this shows that we have access to src and test. Class instances test: " + test + " boost: " + boost);

        return Map.of(
            "message", "pong from Dev Team Demo Server",
            "timestamp", LocalDateTime.now().toString()
        );
    }

    // TODO run a simple demo test
    // GET test-demo
    // GET test-devteam
    @GetMapping("/test-demo")
    public String testDemo() {
        // TestLauncher.launch(PASSING_CLASS_UNDER_TEST)
        // because of the fatJar
        // TestLauncher.launch(something from test-devteam might work?)
        return "This is brittle. Doesn't seem to be an elegant solution.";
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
