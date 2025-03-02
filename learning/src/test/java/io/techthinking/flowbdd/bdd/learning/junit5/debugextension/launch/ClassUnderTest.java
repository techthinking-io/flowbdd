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

package io.techthinking.flowbdd.bdd.learning.junit5.debugextension.launch;

import io.techthinking.flowbdd.bdd.learning.junit5.debugextension.DebugExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(DebugExtension.class)
class ClassUnderTest {

    /**
     * adds interceptBeforeAllMethod
     */
    @BeforeAll
    public static void beforeAll() {
    }

    /**
     * adds interceptBeforeEachMethod
     */
    @BeforeEach
    public void beforeEach() {
    }

    /**
     * where is interceptAfterAllMethod?
     */
    @AfterAll
    public static void afterAll() {
    }

    /**
     * adds interceptAfterEachMethod
     */
    @AfterEach
    public void afterEach() {
    }

    @Order(0)
    @Test
    void firstTest() {
    }

    @Order(1)
    @Test
    void secondTest() {
    }

    @ParameterizedTest
    @Order(2)
    @ValueSource(ints = { 2, 4, 8 })
    void thirdParamTest(int value) {
        assertThat(value % 2).isEqualTo(0);
    }
}