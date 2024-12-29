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

package com.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture.methods;

import org.junit.jupiter.api.extension.ExtensionContext;

public class BaseMethod {
    private final String name;
    private final ExtensionContext extensionContext;

    public BaseMethod(String name, ExtensionContext extensionContext) {
        this.name = name;
        this.extensionContext = extensionContext;
    }

    public String getName() {
        return name;
    }

    public ExtensionContext getExtensionContext() {
        return extensionContext;
    }

    public String getClassName() {
        return extensionContext.getRequiredTestClass().getSimpleName();
    }

    @Override
    public String toString() {
        return "BaseMethod{" + verbose() + '}';
    }

    public String verbose() {
        return "name='" + name + '\'' +
            ", extensionContext=" + extensionContext +
            ", class=" + extensionContext.getRequiredTestClass().getSimpleName() +
            ", method=" + extensionContext.getTestMethod();
    }
}
