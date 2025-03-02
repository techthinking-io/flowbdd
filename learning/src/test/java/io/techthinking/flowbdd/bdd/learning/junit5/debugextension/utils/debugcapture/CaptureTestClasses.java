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

package io.techthinking.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture;

import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CaptureTestClasses {
    private final ConcurrentHashMap<String, CaptureTestClass> capturedClasses = new ConcurrentHashMap<>();

    public List<String> getClasses() {
        return Collections.list(capturedClasses.keys());
    }

    public ConcurrentHashMap<String, CaptureTestClass> getCapturedClasses() {
        return capturedClasses;
    }

    public CaptureTestClass getCaptureTestClass(ExtensionContext extensionContext) {
        return capturedClasses.get(getClassName(extensionContext));
    }

    public CaptureTestClass newCaptureTestClass(ExtensionContext context) {
        CaptureTestClass captureTestClass = new CaptureTestClass();
        capturedClasses.put(getClassName(context), captureTestClass);
        return captureTestClass;
    }

    public String getClassName(ExtensionContext extensionContext) {
        return extensionContext.getRequiredTestClass().getSimpleName();
    }
}
