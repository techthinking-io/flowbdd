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

package io.techthinking.flowbdd.bdd.learning.junit5.debugextension;

import io.techthinking.flowbdd.bdd.learning.junit5.debugextension.undertest.ClassUnderTest1;
import io.techthinking.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture.CaptureTestClass;
import io.techthinking.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture.CaptureTestMethod;
import io.techthinking.flowbdd.bdd.learning.junit5.launcher.TestLauncher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class DebugExtensionComponentTest {

    @BeforeEach
    void setUp() {
        DebugExtension.reset();
    }

    @Test
    void debugTestsInClass() {
        TestLauncher.launch(ClassUnderTest1.class);

        assertThat(DebugExtension.getCapturedTestClasses().getClasses()).containsExactly("ClassUnderTest1");
        verifyClassUnderTestMethods("ClassUnderTest1", "class1_");
    }

    @Test
    void debugTestsInPackage() {
        TestLauncher.launch(selectPackage(ClassUnderTest1.class.getPackage().getName()));

        assertThat(DebugExtension.getCapturedTestClasses().getClasses()).containsExactly("ClassUnderTest1", "ClassUnderTest2");
        verifyClassUnderTestMethods("ClassUnderTest1", "class1_");
        verifyClassUnderTestMethods("ClassUnderTest2", "class2_");
    }

    private void verifyClassUnderTestMethods(String className, String methodPrefix) {
        CaptureTestClass capturedTestClass = DebugExtension.getCapturedTestClasses().getCapturedClasses().get(className);
        assertThat(capturedTestClass).isNotNull();
        assertThat(capturedTestClass.getCapturedMethodsForClass().getCapturedMethodNames()).containsExactly(
            "beforeAll",
            "interceptBeforeAllMethod",
            "interceptTestClassConstructor", // firstTest
            "interceptTestClassConstructor", // secondTest
            "interceptTestClassConstructor", // thirdParamTest param 1
            "interceptTestClassConstructor", // thirdParamTest param 2
            "interceptTestClassConstructor", // thirdParamTest param 3
            "interceptAfterAllMethod",
            "afterAll"
        );

        assertThat(capturedTestClass.getMethodNames()).containsExactly(
            methodPrefix + "firstTest",
            methodPrefix + "secondTest",
            methodPrefix + "thirdParamTest",
            methodPrefix + "thirdParamTest",
            methodPrefix + "thirdParamTest"
        );

        CaptureTestMethod capturedFirstTestMethod = capturedTestClass.getCapturedTestMethod(methodPrefix + "firstTest");
        assertThat(capturedFirstTestMethod.getCapturedMethodNames()).containsExactly(
            "beforeEach",
            "interceptBeforeEachMethod",
            "interceptTestMethod",
            "interceptAfterEachMethod",
            "afterEach");

        CaptureTestMethod capturedSecondTestMethod = capturedTestClass.getCapturedTestMethod(methodPrefix + "secondTest");
        assertThat(capturedSecondTestMethod.getCapturedMethodNames()).containsExactly(
            "beforeEach",
            "interceptBeforeEachMethod",
            "interceptTestMethod",
            "interceptAfterEachMethod",
            "afterEach");

        List<CaptureTestMethod> capturedThirdParamTestMethods = capturedTestClass.getCapturedTestMethods(methodPrefix + "thirdParamTest");
        capturedThirdParamTestMethods.forEach(captureTestMethod -> {
            assertThat(captureTestMethod.getCapturedMethodNames()).containsExactly(
                "beforeEach",
                "interceptBeforeEachMethod",
                "interceptTestTemplateMethod", // notice that this is interceptTestTemplateMethod
                "interceptAfterEachMethod",
                "afterEach");
        });
    }
}
