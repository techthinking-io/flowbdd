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

package io.techthinking.flowbdd.examples.devteamdemo;

import io.techthinking.flowbdd.examples.devteam.bdd.DevTeamSimulatorTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestAccessVerification {

    @Test
    void canAccessDevTeamTests() {
        DevTeamSimulatorTest test = new DevTeamSimulatorTest();
        assertNotNull(test);
        System.out.println("[DEBUG_LOG] Successfully accessed DevTeamSimulatorTest from devteam-demo");
    }
}
