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

package component.results.scenarios;

import component.results.AbstractResultsForTestSuite;
import org.junit.jupiter.api.Test;
import shared.undertest.basic.DisabledTestCasesUnderTest;

import static io.bitsmart.bdd.report.junit5.results.model.TestSuiteTotalsBuilder.aTestSuiteResultsMetadata;
import static org.assertj.core.api.Assertions.assertThat;

public class DisabledResultsTest extends AbstractResultsForTestSuite {

    @Override
    public Class<?> classUnderTest() {
        return DisabledTestCasesUnderTest.class;
    }

    @Test
    void verifyResultsForDisabledTestCases() {
        assertThat(testSuiteResult().getTotals()).isEqualTo(
            aTestSuiteResultsMetadata()
                .withTestCaseCount(2)
                .withSkippedCount(2)
                .build());
    }
}
