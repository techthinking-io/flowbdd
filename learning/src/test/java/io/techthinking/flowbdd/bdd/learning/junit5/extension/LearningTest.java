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

package io.techthinking.flowbdd.bdd.learning.junit5.extension;

import io.techthinking.flowbdd.bdd.learning.junit5.annotations.Given;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.logging.Logger;

@Tag("classtag")
@ExtendWith(LearningExtension.class)
public class LearningTest {
    private static final Logger logger = Logger.getLogger(LearningExtension.class.getName());

    @BeforeEach
    void init(TestInfo testInfo, TestReporter testReporter) {
        String callingTest = testInfo.getTestMethod().get().getName();
        logger.info("Before: " + callingTest);
        logger.info("Before testInfo: " + testInfo);
        logger.info("Before testReporter: " + testReporter);
    }

    @AfterEach
    void after(TestInfo testInfo, TestReporter testReporter) {
        String callingTest = testInfo.getTestMethod().get().getName();
        logger.info("After: " + callingTest);
        logger.info("After testInfo: " + testInfo);
        logger.info("Before testReporter: " + testReporter);
    }

    @Test
    @Tag("methodtag")
    @Given("some given")
    void firstTest() {
        logger.info("1");
    }
}
