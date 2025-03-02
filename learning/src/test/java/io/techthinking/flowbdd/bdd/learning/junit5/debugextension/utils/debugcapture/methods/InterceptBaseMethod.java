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

package io.techthinking.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture.methods;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import java.lang.reflect.Method;

public class InterceptBaseMethod extends BaseMethod {
    private final InvocationInterceptor.Invocation<Void> invocation;
    private final ReflectiveInvocationContext<Method> invocationContext;

    public InterceptBaseMethod(
        String name,
        InvocationInterceptor.Invocation<Void> invocation,
        ReflectiveInvocationContext<Method> invocationContext,
        ExtensionContext extensionContext)
    {
        super(name, extensionContext);
        this.invocation = invocation;
        this.invocationContext = invocationContext;
    }

    public InvocationInterceptor.Invocation<Void> getInvocation() {
        return invocation;
    }

    public ReflectiveInvocationContext<Method> getInvocationContext() {
        return invocationContext;
    }

    @Override
    public String toString() {
        return verbose();
    }

    public String verbose() {
        return super.verbose() +
            ", arguments=" + invocationContext.getArguments();
    }
}
