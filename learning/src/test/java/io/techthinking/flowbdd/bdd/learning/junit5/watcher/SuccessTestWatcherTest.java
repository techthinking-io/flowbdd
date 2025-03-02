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

package io.techthinking.flowbdd.bdd.learning.junit5.watcher;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SuccessTestWatcher.class)
class SuccessTestWatcherTest {

    @BeforeAll
    public static void reset() {
        SuccessTestWatcher.clearResults();
    }

    @Order(0)
    @Test
    void firstTest() {
        assertThat(true).isTrue();
    }

    @Order(1)
    @Test
    void secondTest() {
        assertThat(SuccessTestWatcher.getResults()).hasSize(1);
        assertThat(SuccessTestWatcher.getResults().get(0)).isEqualTo("success");
    }
}