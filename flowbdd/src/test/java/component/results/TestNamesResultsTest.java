/*
 * Smart BDD - The smart way to do behavior-driven development.
 * Copyright (C)  2021  James Bayliss
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

package component.results;

import io.bitsmart.bdd.report.junit5.results.model.TestCaseResult;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import shared.undertest.basic.TestNamesTest;

import static org.assertj.core.api.Assertions.assertThat;

public class TestNamesResultsTest extends AbstractResultsForTestSuite {

    @Override
    public Class<?> classUnderTest() {
        return TestNamesTest.class;
    }

    @Test
    void verifyFirstTest_noParams() {
        TestCaseResult testCaseResult = testCaseResult(0);
        assertThat(testCaseResult.getName()).isEqualTo("testMethod");
        assertThat(testCaseResult.getDisplayName()).isEqualTo(("Test method"));
    }

    @Test
    void verifySecondTest_paramsNoCustomName() {
        assertThat(testCaseResult(1).getName()).isEqualTo("paramTest");
        assertThat(testCaseResult(1).getDisplayName()).isEqualTo("Param test value 1");

        assertThat(testCaseResult(2).getName()).isEqualTo("paramTest");
        assertThat(testCaseResult(2).getDisplayName()).isEqualTo("Param test value 2");

        assertThat(testCaseResult(3).getName()).isEqualTo("paramTest");
        assertThat(testCaseResult(3).getDisplayName()).isEqualTo("Param test value 3");
    }

    @Test
    void verifyThirdTest_paramsWithCustomName() {
        assertThat(testCaseResult(4).getName()).isEqualTo("paramTestWithCustomName");
        assertThat(testCaseResult(4).getDisplayName()).isEqualTo("index:1 arg = value 1");

        assertThat(testCaseResult(5).getName()).isEqualTo("paramTestWithCustomName");
        assertThat(testCaseResult(5).getDisplayName()).isEqualTo("index:2 arg = value 2");

        assertThat(testCaseResult(6).getName()).isEqualTo("paramTestWithCustomName");
        assertThat(testCaseResult(6).getDisplayName()).isEqualTo("index:3 arg = value 3");
    }
}
