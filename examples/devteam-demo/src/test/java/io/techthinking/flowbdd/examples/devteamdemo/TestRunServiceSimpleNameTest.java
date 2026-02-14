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

package io.techthinking.flowbdd.examples.devteamdemo;

import io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorTest;
import io.techthinking.flowbddserver.core.TestRunService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestRunServiceSimpleNameTest {

    @Autowired
    private TestRunService testRunService;

    @Test
    void leavesFullClassNameAsIs() {
        String resolved = testRunService.resolveClassName("io.techthinking.flowbddserver.FlowBddServerApplicationTests");
        assertThat(resolved).isEqualTo("io.techthinking.flowbddserver.FlowBddServerApplicationTests");
    }

    /** From src package */
    @Disabled("Issue with running all tests ./gradlew test - this fails")
    // @Test
    void canResolveDevProductivityBoost() {
        // Checking that we have access to the test class
        DevTeamSimulatorTest test = new DevTeamSimulatorTest();

        String resolved = testRunService.resolveClassName("ProductivityBoost");
        assertThat(resolved).isEqualTo("io.techthinking.flowbdd.examples.devteam.model.ProductivityBoost");
    }

    /** From test package */
    @Disabled("Issue with running all tests ./gradlew test - this fails")
    // @Test
    void canResolveDevTeamSimulatorTest() {
        // Checking that we have access to the test class
        DevTeamSimulatorTest test = new DevTeamSimulatorTest();

        String resolved = testRunService.resolveClassName("DevTeamSimulatorTest");
        assertThat(resolved).isEqualTo("io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorTest");
    }
}
