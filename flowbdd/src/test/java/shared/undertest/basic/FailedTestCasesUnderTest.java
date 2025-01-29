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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * To generate run `./gradlew test` there are located build/test-results/test/
 *
 * <?xml version="1.0" encoding="UTF-8"?>
 * <testsuite name="junit5.results.undertest.FailedTestCasesUnderTest" tests="4" skipped="0" failures="4" errors="0" timestamp="2021-04-12T20:09:43" hostname="Jamess-MacBook-Pro.local" time="0.189">
 *   <properties/>
 *   <testcase name="testMethod()" classname="junit5.results.undertest.FailedTestCasesUnderTest" time="0.12">
 *     <failure message="java.lang.AssertionError: &#10;Expecting:&#10; &lt;&quot;testMethod&quot;&gt;&#10;not to be equal to:&#10; &lt;&quot;testMethod&quot;&gt;&#10;" type="java.lang.AssertionError">java.lang.AssertionError:
 * Expecting:
 *  &lt;&quot;testMethod&quot;&gt;
 * not to be equal to:
 *  &lt;&quot;testMethod&quot;&gt;
 *
 * 	at junit5.results.undertest.FailedTestCasesUnderTest.testMethod(FailedTestCasesUnderTest.java:16) ...
 *   </testcase>
 *   <system-out><![CDATA[]]></system-out>
 *   <system-err><![CDATA[]]></system-err>
 * </testsuite>
 */
@ExtendWith(FlowBDD.class)
@EnabledIf("isEnabled")
public class FailedTestCasesUnderTest {
    private static Boolean enabled = false;

    @Test
    void testMethod() {
        failingAssertion();
    }

    @ParameterizedTest
    @ValueSource(strings = { "value 1", "value 2", "value 3" })
    void paramTest(String param) {
        failingAssertionWith(param);
    }

    private void failingAssertion() {
        assertThat(true).isFalse();
    }

    private void failingAssertionWith(String param) {
        assertThat(param).isNull();
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(Boolean isEnabled) {
        enabled = isEnabled;
    }
}