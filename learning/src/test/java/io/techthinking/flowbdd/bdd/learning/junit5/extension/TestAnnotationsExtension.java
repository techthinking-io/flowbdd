/*
 * Flow BDD - The productive way to test.
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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestAnnotationsExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    static boolean beforeAllCalled = false;

    @BeforeAll
    public static void beforeAll() {
        beforeAllCalled = true;
    }

    public static boolean isBeforeAllCalled() {
        return beforeAllCalled;
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {}

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception { }

}
