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

package io.techthinking.flowbdd.bdd.learning.junit5.debugextension;

import io.techthinking.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture.CaptureTestClass;
import io.techthinking.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture.CaptureTestClasses;
import io.techthinking.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture.CaptureTestMethod;
import io.techthinking.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture.methods.Callback;
import io.techthinking.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture.methods.DynamicTest;
import io.techthinking.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture.methods.InterceptTestFactoryBaseMethod;
import io.techthinking.flowbdd.bdd.learning.junit5.debugextension.utils.debugcapture.methods.TestClassConstructor;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Used for learning
 *
 * Potentially we can add - BeforeTestExecutionCallback, AfterTestExecutionCallback
 */
public class DebugExtension implements
    BeforeAllCallback, BeforeEachCallback, AfterAllCallback, AfterEachCallback, TestWatcher, InvocationInterceptor {
    private static final List<String> allMethodNames = new ArrayList<>();
    private static CaptureTestClasses captureTestClasses = new CaptureTestClasses();

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        CaptureTestClass captureTestClass = captureTestClasses.newCaptureTestClass(context);
        captureTestClass.add(new Callback("beforeAll", context));
        allMethodNames.add("beforeAll");
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        CaptureTestClass captureTestClass = captureTestClasses.getCaptureTestClass(context);
        CaptureTestMethod captureTestMethod = captureTestClass.newCaptureTestMethod(context);
        captureTestMethod.add(new Callback("beforeEach", context));
        allMethodNames.add("beforeEach");
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        CaptureTestClass captureTestClass = captureTestClasses.getCaptureTestClass(context);
        captureTestClass.add(new Callback("afterAll", context));
        allMethodNames.add("afterAll");
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        CaptureTestMethod captureTestMethod = getCaptureTestMethod(context);
        captureTestMethod.add(new Callback("afterEach", context));
        allMethodNames.add("afterEach");
    }

    public <T> T interceptTestClassConstructor(Invocation<T> invocation, ReflectiveInvocationContext<Constructor<T>> invocationContext, ExtensionContext extensionContext) throws Throwable {
        CaptureTestClass captureTestClass = captureTestClasses.getCaptureTestClass(extensionContext);
        captureTestClass.add(new TestClassConstructor<>("interceptTestClassConstructor", invocation, invocationContext, extensionContext));
        allMethodNames.add("interceptTestClassConstructor");
        return invocation.proceed();
    }

    public void interceptBeforeAllMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        CaptureTestClass captureTestClass = captureTestClasses.getCaptureTestClass(extensionContext);
        captureTestClass.add("interceptBeforeAllMethod", invocation, invocationContext, extensionContext);
        allMethodNames.add("interceptBeforeAllMethod");
        invocation.proceed();
    }

    public void interceptBeforeEachMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        CaptureTestMethod captureTestMethod = getCaptureTestMethod(extensionContext);
        captureTestMethod.add("interceptBeforeEachMethod", invocation, invocationContext, extensionContext);
        allMethodNames.add("interceptBeforeEachMethod");
        invocation.proceed();
    }

    public void interceptTestMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        CaptureTestMethod captureTestMethod = getCaptureTestMethod(extensionContext);
        captureTestMethod.add("interceptTestMethod", invocation, invocationContext, extensionContext);
        allMethodNames.add("interceptTestMethod");
        invocation.proceed();
    }

    public <T> T interceptTestFactoryMethod(Invocation<T> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        CaptureTestMethod captureTestMethod = getCaptureTestMethod(extensionContext);
        captureTestMethod.add(new InterceptTestFactoryBaseMethod("interceptTestFactoryMethod", invocation, invocationContext, extensionContext));
        allMethodNames.add("interceptTestFactoryMethod");
        return invocation.proceed();
    }

    public void interceptTestTemplateMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        CaptureTestMethod captureTestMethod = getCaptureTestMethod(extensionContext);
        captureTestMethod.add("interceptTestTemplateMethod", invocation, invocationContext, extensionContext);
        allMethodNames.add("name");
        invocation.proceed();
    }

    public void interceptDynamicTest(Invocation<Void> invocation, ExtensionContext extensionContext) throws Throwable {
        CaptureTestMethod captureTestMethod = getCaptureTestMethod(extensionContext);
        captureTestMethod.add(new DynamicTest("interceptDynamicTest", invocation, extensionContext));
        allMethodNames.add("interceptDynamicTest");
        invocation.proceed();
    }

    public void interceptAfterEachMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        CaptureTestMethod captureTestMethod = getCaptureTestMethod(extensionContext);
        captureTestMethod.add("interceptAfterEachMethod", invocation, invocationContext, extensionContext);
        allMethodNames.add("interceptAfterEachMethod");
        invocation.proceed();
    }

    public void interceptAfterAllMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        CaptureTestClass captureTestClass = captureTestClasses.getCaptureTestClass(extensionContext);
        captureTestClass.add("interceptAfterAllMethod", invocation, invocationContext, extensionContext);
        allMethodNames.add("interceptAfterAllMethod");
        invocation.proceed();
    }

    public static List<String> getAllMethodNames() {
        return allMethodNames;
    }

    public static CaptureTestClasses getCapturedTestClasses() {
        return captureTestClasses;
    }

    private CaptureTestMethod getCaptureTestMethod(ExtensionContext context) {
        CaptureTestClass captureTestClass = captureTestClasses.getCaptureTestClass(context);
        CaptureTestMethod captureTestMethod = captureTestClass.getCaptureTestMethod(context);
//        System.out.println("captureTestMethod:" + captureTestMethod);
        return captureTestMethod;
    }

    public static void reset() {
        allMethodNames.clear();
        captureTestClasses = new CaptureTestClasses();
    }
}
