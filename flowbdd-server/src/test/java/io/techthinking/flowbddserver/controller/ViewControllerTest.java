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

package io.techthinking.flowbddserver.controller;

import io.techthinking.flowbdd.report.config.FlowBddConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("flowbdd-view-test-server");
        FlowBddConfig.overrideBasePath(tempDir);
        Files.createDirectories(FlowBddConfig.getDataPath());
    }

    @AfterEach
    void tearDown() {
        FlowBddConfig.overrideBasePath(null);
    }

    @Test
    void testSuiteByClass_returnsView() throws Exception {
        String indexJson = "{\"timeStamp\":\"2026-02-01T10:00:00Z\",\"summary\":{\"tests\":1,\"passed\":1,\"failed\":0,\"aborted\":0,\"skipped\":0},\"links\":{\"testSuites\":[{\"name\":\"io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest\",\"file\":\"TEST-io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest.json\"}]}}";
        Files.writeString(FlowBddConfig.getDataPath().resolve("index.json"), indexJson);

        String suiteJson = "{\"title\":\"MyTitle\",\"name\":\"MyTestSuite\",\"className\":\"io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest\",\"packageName\":\"com.example\",\"testResults\":[],\"summary\":{\"tests\":0,\"passed\":0,\"failed\":0,\"aborted\":0,\"skipped\":0},\"notes\":null}";
        Files.writeString(FlowBddConfig.getDataPath().resolve("TEST-io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest.json"), suiteJson);

        mockMvc.perform(get("/TEST-io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("test-suite"));
    }

    @Test
    void testSuiteByClass_failsWhenIndexMissing() throws Exception {
        mockMvc.perform(get("/TEST-io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testSuiteByClass_failsWhenSuiteFileMissing() throws Exception {
        String indexJson = "{\"timeStamp\":\"2026-02-01T10:00:00Z\",\"summary\":{\"tests\":1,\"passed\":1,\"failed\":0,\"aborted\":0,\"skipped\":0},\"links\":{\"testSuites\":[{\"name\":\"io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest\",\"file\":\"TEST-io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest.json\"}]}}";
        Files.writeString(FlowBddConfig.getDataPath().resolve("index.json"), indexJson);

        mockMvc.perform(get("/TEST-io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testSuiteByClass_reproducesSpelError() throws Exception {
        String indexJson = "{\"timeStamp\":\"2026-02-01T10:00:00Z\",\"summary\":{\"tests\":1,\"passed\":1,\"failed\":0,\"aborted\":0,\"skipped\":0},\"links\":{\"testSuites\":[{\"name\":\"io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest\",\"file\":\"TEST-io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest.json\"}]}}";
        Files.writeString(FlowBddConfig.getDataPath().resolve("index.json"), indexJson);

        String suiteJson = "{" +
                "\"title\":\"MyTitle\"," +
                "\"name\":\"MyTestSuite\"," +
                "\"className\":\"io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest\"," +
                "\"packageName\":\"com.example\"," +
                "\"testResults\":[{" +
                "  \"wordify\":\"some test\"," +
                "  \"status\":\"PASSED\"," +
                "  \"class\": {\"fullyQualifiedName\":\"com.example.MyTest\", \"className\":\"MyTest\", \"packageName\":\"com.example\"}," +
                "  \"method\": {\"name\":\"testMethod\", \"wordify\":\"test method\", \"arguments\":[]}" +
                "}]," +
                "\"summary\":{\"tests\":1,\"passed\":1,\"failed\":0,\"aborted\":0,\"skipped\":0}," +
                "\"notes\":null" +
                "}";
        Files.writeString(FlowBddConfig.getDataPath().resolve("TEST-io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest.json"), suiteJson);

        mockMvc.perform(get("/TEST-io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSuiteByClass_handlesNullMethodInTestSuite() throws Exception {
        String indexJson = "{\"timeStamp\":\"2026-02-01T10:00:00Z\",\"summary\":{\"tests\":1,\"passed\":1,\"failed\":0,\"aborted\":0,\"skipped\":0},\"links\":{\"testSuites\":[{\"name\":\"io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest\",\"file\":\"TEST-io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest.json\"}]}}";
        Files.writeString(FlowBddConfig.getDataPath().resolve("index.json"), indexJson);

        // testResults[0].method is null
        String suiteJson = "{\"title\":\"MyTitle\",\"name\":\"MyTestSuite\",\"className\":\"io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest\",\"packageName\":\"com.example\",\"testResults\":[{\"wordify\":\"some test\",\"status\":\"PASSED\",\"method\":null}],\"summary\":{\"tests\":1,\"passed\":1,\"failed\":0,\"aborted\":0,\"skipped\":0},\"notes\":null}";
        Files.writeString(FlowBddConfig.getDataPath().resolve("TEST-io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest.json"), suiteJson);

        mockMvc.perform(get("/TEST-io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorVerboseTest"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("test-suite"));
    }
}
