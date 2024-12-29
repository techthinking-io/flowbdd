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

package com.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture;

import com.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture.methods.BaseMethod;
import com.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture.methods.InterceptBaseMethod;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaptureTestMethod {
    private final Map<String, BaseMethod> capturedMethods = new HashMap<>();
    private final List<String> capturedMethodsNames = new ArrayList<>();

    public List<String> getCapturedMethodNames() {
        return capturedMethodsNames;
    }

    public Map<String, BaseMethod> getCapturedMethods() {
        return capturedMethods;
    }

    public void add(BaseMethod method) {
        capturedMethodsNames.add(method.getName());
        put(method.getName(), method);
    }

    public void add(
        String name,
        InvocationInterceptor.Invocation<Void> invocation,
        ReflectiveInvocationContext<Method> invocationContext,
        ExtensionContext extensionContext) {
        add(new InterceptBaseMethod(name, invocation, invocationContext, extensionContext));
    }

    private void put(String name, BaseMethod method) {
        capturedMethods.put(name, method);
    }

    @Override
    public String toString() {
        return "ExtensionCalls{" +
            "extensionMethods=" + capturedMethods +
            '}';
    }
}
