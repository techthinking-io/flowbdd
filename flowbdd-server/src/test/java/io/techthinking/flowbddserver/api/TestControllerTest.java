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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@org.springframework.test.annotation.DirtiesContext(classMode = org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Disabled("Need to share the jimfs/in memory file system")
    // @Test
    void status_returnsNullWhenNoTestsRun() throws Exception {
        mockMvc.perform(get("/api/tests/status"))
                .andExpect(status().isOk());
                //.andExpect(content().string(""));
    }

    @Disabled("Need to share the jimfs/in memory file system")
    // @Test
    void run_returnsReportIndex() throws Exception {
        // Use a test that is likely to be on the classpath
        String requestBody = "{\"className\":\"io.techthinking.flowbddserver.FlowBddServerApplicationTests\"}";

        mockMvc.perform(post("/api/tests/run")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timeStamp").isNotEmpty())
                .andExpect(jsonPath("$.summary.tests").isNotEmpty());

        // Check if report index was generated
        mockMvc.perform(get("/api/report/index"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.summary.tests").isNotEmpty());

        // Check if status returns the result
        mockMvc.perform(get("/api/tests/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timeStamp").isNotEmpty());
    }
}
