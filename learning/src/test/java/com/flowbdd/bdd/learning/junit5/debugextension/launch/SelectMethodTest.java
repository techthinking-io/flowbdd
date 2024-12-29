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

package com.flowbdd.bdd.learning.junit5.debugextension.launch;

import com.flowbdd.bdd.learning.junit5.debugextension.DebugExtension;
import com.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture.CaptureTestClass;
import com.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture.CaptureTestMethod;
import com.flowbdd.bdd.learning.junit5.launcher.TestLauncher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod;

public class SelectMethodTest {
    private static final Class<?> CLASS_UNDER_TEST = ClassUnderTest.class;
    private static final String CLASS_UNDER_TEST_NAME = "ClassUnderTest";

    @BeforeEach
    void setUp() {
        DebugExtension.reset();
    }

    /**
     * method no-args
     * re-run a specific including setup
     */
    @Test
    void selectAndRunMethodNoArgs() {
        new TestLauncher();
        TestLauncher.launch(
            CLASS_UNDER_TEST,
            "firstTest");

        assertThat(DebugExtension.getCapturedTestClasses().getClasses()).containsExactly(CLASS_UNDER_TEST_NAME);
        CaptureTestClass capturedTestClass = DebugExtension.getCapturedTestClasses().getCapturedClasses().get(CLASS_UNDER_TEST_NAME);
        assertThat(capturedTestClass).isNotNull();
        assertThat(capturedTestClass.getCapturedMethodsForClass().getCapturedMethodNames()).containsExactly(
            "beforeAll",
            "interceptBeforeAllMethod",
            "interceptTestClassConstructor", // firstTest
            "interceptAfterAllMethod",
            "afterAll"
        );

        assertThat(capturedTestClass.getMethodNames()).containsOnly(
            "firstTest"
        );

        CaptureTestMethod capturedFirstTestMethod = capturedTestClass.getCapturedTestMethod("firstTest");
        assertThat(capturedFirstTestMethod.getCapturedMethodNames()).containsExactly(
            "beforeEach",
            "interceptBeforeEachMethod",
            "interceptTestMethod",
            "interceptAfterEachMethod",
            "afterEach");
    }

    /**
     * method with args
     * re-run a specific including setup
     */
    @Disabled
    @Test
    void selectAndRunMethodWithArgs() {
        TestLauncher.launch(
            selectMethod(CLASS_UNDER_TEST, "thirdParamTest")
        );

        assertThat(DebugExtension.getCapturedTestClasses().getClasses()).containsExactly("TestCase");
        CaptureTestClass capturedTestClass = DebugExtension.getCapturedTestClasses().getCapturedClasses().get("TestCase");

        assertThat(capturedTestClass.getMethodNames()).containsOnly(
            "thirdParamTest"
        );
    }
}
