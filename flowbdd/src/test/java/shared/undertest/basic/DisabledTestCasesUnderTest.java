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

package shared.undertest.basic;

import com.flowbdd.report.junit5.results.extension.FlowBDD;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * To generate run `./gradlew test` there are located build/test-results/test/
 *
 * <?xml version="1.0" encoding="UTF-8"?>
 * <testsuite name="junit5.results.undertest.DisabledTestCasesUnderTest" tests="1" skipped="1" failures="0" errors="0" timestamp="2021-04-12T20:09:43" hostname="Jamess-MacBook-Pro.local" time="0.0">
 *   <properties/>
 *   <testcase name="testMethod()" classname="junit5.results.undertest.DisabledTestCasesUnderTest" time="0.0">
 *     <skipped/>
 *   </testcase>
 *   <system-out><![CDATA[]]></system-out>
 *   <system-err><![CDATA[]]></system-err>
 * </testsuite>
 */
@ExtendWith(FlowBDD.class)
public class DisabledTestCasesUnderTest {

    @Disabled
    @Test
    void testMethod() {
        disabledAssertion();
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(strings = { "value 1", "value 2", "value 3" })
    void paramTest(String param) {
        disabledAssertionWith(param);
    }

    private void disabledAssertion() {
        assertThat(true).isTrue();
    }

    private void disabledAssertionWith(String param) {
        assertThat(param).isNotNull();
    }
}