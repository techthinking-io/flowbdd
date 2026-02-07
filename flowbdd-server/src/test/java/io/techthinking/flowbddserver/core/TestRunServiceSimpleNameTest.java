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

package io.techthinking.flowbddserver.core;

import io.techthinking.flowbddserver.api.RunRequest;
import io.techthinking.flowbdd.report.report.model.DataReportIndex;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestRunServiceSimpleNameTest {

    @Autowired
    private TestRunService testRunService;

    @Test
    void canResolveSimpleClassName() {
        String resolved = testRunService.resolveClassName("FlowBddServerApplicationTests");
        assertThat(resolved).isEqualTo("io.techthinking.flowbddserver.FlowBddServerApplicationTests");
    }

    @Test
    void leavesFullClassNameAsIs() {
        String resolved = testRunService.resolveClassName("io.techthinking.flowbddserver.FlowBddServerApplicationTests");
        assertThat(resolved).isEqualTo("io.techthinking.flowbddserver.FlowBddServerApplicationTests");
    }
}
