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

package component.report.wip;

import io.techthinking.flowbdd.report.junit5.launcher.TestLauncher;
import io.techthinking.flowbdd.report.junit5.results.extension.FlowBDD;
import io.techthinking.flowbdd.report.report.adapter.ReportFactory;
import io.techthinking.flowbdd.report.report.model.Clazz;
import io.techthinking.flowbdd.report.report.model.Method;
import io.techthinking.flowbdd.report.report.model.Report;
import io.techthinking.flowbdd.report.report.model.Status;
import io.techthinking.flowbdd.report.report.model.TestCase;
import io.techthinking.flowbdd.report.report.model.TestSuite;
import io.techthinking.flowbdd.report.report.model.VersionInfo;
import io.techthinking.flowbdd.report.report.model.builders.ClazzBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.undertest.basic.ClassUnderTest;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static component.report.wip.ReportComponentBuilderWipTest.AssertTestCase.assertTestCase;
import static io.techthinking.flowbdd.report.report.model.builders.ClazzBuilder.aClazz;
import static io.techthinking.flowbdd.report.report.model.builders.MethodBuilder.aMethod;
import static io.techthinking.flowbdd.report.report.model.builders.TestCaseBuilder.aTestCase;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *  WIP new idea.
 *
 *  TODO May need a new package for this - exploring different builders/tests -
 *  Ideas for naming the concept - AssertionStrategy, AssertionStyle, AssertionSugar
 *
 *  Give it a name - FieldAssertionBuilder
 *  and compare with alternatives i.e. ignoringFields
 */
public class ReportComponentBuilderWipTest {
    private static final Class<?> CLASS_UNDER_TEST = ClassUnderTest.class;

    @BeforeEach
    void setUp() {
        FlowBDD.getTestContext().reset();
    }

    @Test
    void createReport() {
        TestLauncher.launch(CLASS_UNDER_TEST);

        Report report = ReportFactory.create(FlowBDD.getTestContext().getTestResults(), testVersionInfo());
        assertThat(report).isNotNull();
        assertThat(report.getTestCases()).hasSize(6);

        assertThat(report.getTestSuites()).hasSize(1);
        TestSuite testSuite = report.getTestSuites().get(0);

        // use contains as the last statement
//        assertForTestSuite(testSuite)
//            .thatNameIs("shared.undertest.ClassUnderTest")
//            .thatClassNameIs("ClassUnderTest")
//            .thatPackageNameIs("shared.undertest")
//            .thatMethodNamesContainsExactly("testMethod", "paramTest", "paramTest", "paramTest")
//            .thatTestCaseContains(firstTestCase());

        AssertTestSuite.assertTestSuite(testSuite)
            .withName("shared.undertest.basic.ClassUnderTest")
            .withClassName("ClassUnderTest")
            .withPackageName("shared.undertest.basic")
            .withTestCaseContains(firstTestCase());

        // use contains as the last statement
        AssertTestSuite.assertTestSuite(testSuite)
            .withName("shared.undertest.basic.ClassUnderTest")
            .withClassName("ClassUnderTest")
            .withPackageName("shared.undertest.basic")
            .withTestCase(
                assertTestCase(testSuite.getTestCases().get(0))
                    .withWordify("Passing assertion")
                    .withStatus(Status.PASSED)
                    .withMethod(aMethod().withWordify("TODO").build())
                    .withClazz(aClazz().withClassName("TODO").build())
            );
    }

    static class AssertTestSuite {
        private final TestSuite testSuite;

        private AssertTestSuite(TestSuite testSuite) {
            this.testSuite = testSuite;
        }

        protected static AssertTestSuite assertTestSuite(TestSuite testSuite) {
            return new AssertTestSuite(testSuite);
        }

        protected AssertTestSuite withName(String name) {
            assertThat(testSuite.getName()).isEqualTo(name);
            return this;
        }

        protected AssertTestSuite withClassName(String className) {
            assertThat(testSuite.getClassName()).isEqualTo(className);
            return this;
        }

        protected AssertTestSuite withPackageName(String packageName) {
            assertThat(testSuite.getPackageName()).isEqualTo(packageName);
            return this;
        }

        protected AssertTestSuite withTestCaseContains(TestCase testCase) {
            assertThat(testSuite.getTestCases()).contains(testCase);
            return this;
        }

        protected AssertTestSuite withTestCase(AssertTestCase assertTestCase) {
            return this;
        }
    }

    static class AssertTestCase {
        private final TestCase testCase;

        private AssertTestCase(TestCase testCase) {
            this.testCase = testCase;
        }

        protected static AssertTestCase assertTestCase(TestCase testCase) {
            return new AssertTestCase(testCase);
        }

        protected AssertTestCase withWordify(String wordify) {
            assertThat(testCase.getWordify()).isEqualTo(wordify);
            return this;
        }

        protected AssertTestCase withStatus(Status status) {
            assertThat(testCase.getStatus()).isEqualTo(status);
            return this;
        }

        protected AssertTestCase withMethod(Method method) {
            // TODO this would be MethodAssert
            //assertThat(testCase.getMethod()).isEqualTo(method);
            return this;
        }

        protected AssertTestCase withClazz(Clazz clazz) {
            // TODO this would be ClazzAssert
            //assertThat(testCase.getClazz()).isEqualTo(clazz);
            return this;
        }
    }

    private TestCase firstTestCase() {
        return aTestCase()
            .withWordify("Passing assertion")
            .withStatus(Status.PASSED)
            .withMethod(aMethod().withName("testMethod").withWordify("Test method"))
            .withClazz(aDefaultClazz())
            .build();
    }

    private static ClazzBuilder aDefaultClazz() {
        return aClazz().withClassName("ClassUnderTest").withPackageName("shared.undertest.basic").withFullyQualifiedName("shared.undertest.basic.ClassUnderTest");
    }

    protected VersionInfo testVersionInfo() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(Clock.systemDefaultZone());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS'Z'").withZone(ZoneId.systemDefault());
        String dateTimeAsString = formatter.format(zonedDateTime);
        return new VersionInfo(zonedDateTime, dateTimeAsString, "hostame");
    }
}
