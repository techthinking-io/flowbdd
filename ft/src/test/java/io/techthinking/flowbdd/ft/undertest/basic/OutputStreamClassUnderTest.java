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

package io.techthinking.flowbdd.ft.undertest.basic;

import io.techthinking.flowbdd.report.junit5.results.extension.FlowBdd;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(FlowBdd.class)
@EnabledIf("isEnabled")
@TestMethodOrder(OrderAnnotation.class)
public class OutputStreamClassUnderTest {
    private static Boolean enabled = false;

    @Order(0)
    @Test
    void testMethod() {
        System.out.println("system.out for testMethod");
        System.err.println("system.err for testMethod");
        passingAssertion();
    }

    private void passingAssertion() {
        assertThat(true).isTrue();
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(Boolean isEnabled) {
        enabled = isEnabled;
    }
}