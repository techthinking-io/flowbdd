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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("flowbdd-test-server");
        FlowBddConfig.overrideBasePath(tempDir);
        Files.createDirectories(FlowBddConfig.getDataPath());
    }

    @AfterEach
    void tearDown() {
        FlowBddConfig.overrideBasePath(null);
    }

    @Test
    void getIndex_returnsReportIndex() throws Exception {
        String json = "{\"timeStamp\":\"2026-02-01T10:00:00Z\",\"summary\":{\"count\":1,\"passed\":1,\"failed\":0,\"aborted\":0,\"skipped\":0},\"links\":{\"testSuites\":[]}}";
        Files.writeString(FlowBddConfig.getDataPath().resolve("index.json"), json);

        mockMvc.perform(get("/api/report/index"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timeStamp").value("2026-02-01T10:00:00Z"))
                .andExpect(jsonPath("$.summary.passed").value(1));
    }

    @Test
    void getTestSuite_returnsTestSuite() throws Exception {
        String json = "{\"title\":\"MyTitle\",\"name\":\"MyTestSuite\",\"className\":\"com.example.MyTest\",\"packageName\":\"com.example\",\"testResults\":[],\"summary\":{\"count\":0,\"passed\":0,\"failed\":0,\"aborted\":0,\"skipped\":0},\"notes\":null}";
        Files.writeString(FlowBddConfig.getDataPath().resolve("MyTestSuite.json"), json);

        mockMvc.perform(get("/api/report/suite/MyTestSuite"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MyTestSuite"));
    }

    @Test
    void getIndex_returns404IfNotFound() throws Exception {
        mockMvc.perform(get("/api/report/index"))
                .andExpect(status().isNotFound());
    }
}
