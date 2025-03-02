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

package component.report;

import io.techthinking.flowbdd.report.report.model.builders.ArgumentBuilder;
import io.techthinking.flowbdd.report.report.model.builders.ClazzBuilder;
import io.techthinking.flowbdd.report.report.model.Status;
import io.techthinking.flowbdd.report.report.model.TestCase;
import io.techthinking.flowbdd.report.report.model.TestSuite;

import static io.techthinking.flowbdd.report.report.model.builders.ArgumentBuilder.anArgument;
import static io.techthinking.flowbdd.report.report.model.builders.ClazzBuilder.aClazz;
import static io.techthinking.flowbdd.report.report.model.builders.MethodBuilder.aMethod;
import static io.techthinking.flowbdd.report.report.model.builders.TestCaseBuilder.aTestCase;
import static io.techthinking.flowbdd.report.report.model.builders.TestSuiteSummaryBuilder.aTestSuiteSummary;
import static org.assertj.core.api.Assertions.assertThat;

public class ReportAssertions {

    public static void assertPassingTestSuite(TestSuite testSuite) {
        assertThat(testSuite.getName()).isEqualTo("shared.undertest.basic.ClassUnderTest");
        assertThat(testSuite.getClassName()).isEqualTo("ClassUnderTest");
        assertThat(testSuite.getPackageName()).isEqualTo("shared.undertest.basic");
        assertThat(testSuite.getSummary()).isEqualTo(
            aTestSuiteSummary()
                .withTestCase(6)
                .withPassed(6)
                .build());
        assertThat(testSuite.getTestCases().get(0)).isEqualTo(passingTestCase());
        assertThat(testSuite.getTestCases().get(1)).isEqualTo(passingParamTestCase());
    }

    /**
     * TestCase{wordify='Passing assertion', status=PASSED, cause=null, method=Method{name='testMethod', wordify='Test method', arguments=[]}, clazz=Clazz{fullyQualifiedName='shared.undertest.basic.ClassUnderTest', className='ClassUnderTest', packageName='shared.undertest.basic'}, notes=null, timings=TestCaseTimings{beforeEach=0, afterEach=0, underTest=0, total=0}},
     * TestCase{wordify='Passing assertion with one param value 1', status=PASSED, cause=null, method=Method{name='paramTest', wordify='Param test value 1', arguments=[Argument{clazz=Clazz{fullyQualifiedName='java.lang.String', className='String', packageName='java.lang'}, value='value 1'}]}, clazz=Clazz{fullyQualifiedName='shared.undertest.basic.ClassUnderTest', className='ClassUnderTest', packageName='shared.undertest.basic'}, notes=null, timings=TestCaseTimings{beforeEach=0, afterEach=0, underTest=0, total=0}},
     * TestCase{wordify='Passing assertion with one param value 2', status=PASSED, cause=null, method=Method{name='paramTest', wordify='Param test value 2', arguments=[Argument{clazz=Clazz{fullyQualifiedName='java.lang.String', className='String', packageName='java.lang'}, value='value 2'}]}, clazz=Clazz{fullyQualifiedName='shared.undertest.basic.ClassUnderTest', className='ClassUnderTest', packageName='shared.undertest.basic'}, notes=null, timings=TestCaseTimings{beforeEach=0, afterEach=0, underTest=0, total=0}},
     * TestCase{wordify='Passing assertion with one param value 3', status=PASSED, cause=null, method=Method{name='paramTest', wordify='Param test value 3', arguments=[Argument{clazz=Clazz{fullyQualifiedName='java.lang.String', className='String', packageName='java.lang'}, value='value 3'}]}, clazz=Clazz{fullyQualifiedName='shared.undertest.basic.ClassUnderTest', className='ClassUnderTest', packageName='shared.undertest.basic'}, notes=null, timings=TestCaseTimings{beforeEach=0, afterEach=0, underTest=0, total=0}},
     * TestCase{wordify='Passing assertion with two params null value 4', status=PASSED, cause=null, method=Method{name='paramTestWithNulls', wordify='Param test with nulls null, value 4', arguments=[null, Argument{clazz=Clazz{fullyQualifiedName='java.lang.String', className='String', packageName='java.lang'}, value='value 4'}]}, clazz=Clazz{fullyQualifiedName='shared.undertest.basic.ClassUnderTest', className='ClassUnderTest', packageName='shared.undertest.basic'}, notes=null, timings=TestCaseTimings{beforeEach=0, afterEach=0, underTest=0, total=0}},
     * TestCase{wordify='Passing assertion with two params value 5 null', status=PASSED, cause=null, method=Method{name='paramTestWithNulls', wordify='Param test with nulls value 5, null', arguments=[Argument{clazz=Clazz{fullyQualifiedName='java.lang.String', className='String', packageName='java.lang'}, value='value 5'}, null]}, clazz=Clazz{fullyQualifiedName='shared.undertest.basic.ClassUnderTest', className='ClassUnderTest', packageName='shared.undertest.basic'}, notes=null, timings=TestCaseTimings{beforeEach=0, afterEach=0, underTest=0, total=0}}]>
     */
    public static TestCase passingTestCase() {
        return aTestCase()
            .withWordify("Passing assertion")
            .withStatus(Status.PASSED)
            .withMethod(aMethod().withName("testMethod").withWordify("Test method"))
            .withClazz(aDefaultClazz())
            .build();
    }

    public static TestCase passingParamTestCase() {
        return aTestCase()
            .withWordify("Passing assertion with one param value 1")
            .withStatus(Status.PASSED)
            .withMethod(aMethod().withName("paramTest").withWordify("Param test value 1").withArgument(aStringArgument("value 1")))
            .withClazz(aDefaultClazz())
            .build();
    }

    private static ArgumentBuilder aStringArgument(String value) {
        return anArgument().withClazz(aStringClazz()).withValue(value);
    }

    private static ClazzBuilder aStringClazz() {
        return aClazz().withClassName("String").withPackageName("java.lang").withFullyQualifiedName("java.lang.String");
    }

    private static ClazzBuilder aDefaultClazz() {
        return aClazz().withClassName("ClassUnderTest").withPackageName("shared.undertest.basic").withFullyQualifiedName("shared.undertest.basic.ClassUnderTest");
    }
}
