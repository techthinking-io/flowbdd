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

package io.techthinking.flowbdd.report.junit5.results.extension;

import io.techthinking.flowbdd.report.junit5.results.model.TestCaseNameFactory;
import io.techthinking.flowbdd.report.junit5.results.model.TestCaseResult;
import io.techthinking.flowbdd.report.junit5.results.model.TestResults;
import io.techthinking.flowbdd.report.report.model.Report;
import io.techthinking.flowbdd.report.report.model.VersionInfo;
import io.techthinking.flowbdd.report.junit5.results.model.TestSuiteResult;
import io.techthinking.flowbdd.report.report.writers.ReportWriter;
import io.techthinking.flowbdd.wordify.WordifyExtensionContext;
import io.techthinking.flowbdd.report.junit5.results.model.TestCaseResultStatus;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.junit.jupiter.params.ParameterizedTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Optional;

import static io.techthinking.flowbdd.report.junit5.results.model.TestCaseResultStatus.ABORTED;
import static io.techthinking.flowbdd.report.junit5.results.model.TestCaseResultStatus.DISABLED;
import static io.techthinking.flowbdd.report.junit5.results.model.TestCaseResultStatus.FAILED;
import static io.techthinking.flowbdd.report.junit5.results.model.TestCaseResultStatus.PASSED;
import static io.techthinking.flowbdd.wordify.tokenize.WordifyStringUtil.upperCaseFirstChar;
import static io.techthinking.flowbdd.wordify.tokenize.WordifyStringUtil.wordifyMethodOrFieldName;

/**
 *
 * Possible todo
 *   private static final LocalDateTime dateTime = LocalDateTime.now();
 *   private static final ReportWriter reportWriter = new ReportWriter(dateTime);
 */
public class TestContext implements
    BeforeAllCallback, BeforeEachCallback, AfterAllCallback, AfterEachCallback, TestWatcher, InvocationInterceptor {
    private final TestResults testResults;
    private final WordifyExtensionContext wordifyExtensionContext;
    private final TestCaseNameFactory testCaseNameFactory;
    private final ReportWriter reportWriter;
    private boolean isReporting = false;

    public TestContext(
        TestResults testResults,
        WordifyExtensionContext wordifyExtensionContext,
        TestCaseNameFactory testCaseNameFactory,
        ReportWriter reportWriter)
    {
        this.testResults = testResults;
        this.wordifyExtensionContext = wordifyExtensionContext;
        this.testCaseNameFactory = testCaseNameFactory;
        this.reportWriter = reportWriter;
    }

    /** Start test suite */
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        startReporting();
        testResults.startTestSuite(context);
    }

    /** Start test case */
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        getTestSuiteResult(context).startTestCase(context);
    }

    /** Complete test suite */
    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        getTestSuiteResult(context).completeTestSuite();
        //writeTestSuiteResults();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
    }

    @Override
    public <T> T interceptTestClassConstructor(Invocation<T> invocation, ReflectiveInvocationContext<Constructor<T>> invocationContext, ExtensionContext extensionContext) throws Throwable {
        return invocation.proceed();
    }

    @Override
    public void interceptBeforeAllMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        invocation.proceed();
    }

    @Override
    public void interceptBeforeEachMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        invocation.proceed();
    }

    /** Update test case - normal test without params */
    @Override
    public void interceptTestMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        updateTestCaseResult(invocationContext, extensionContext);
        invocation.proceed();
    }

    /** Update test case - Using (at)TestFactory??? Not tested. */
    @Override
    public <T> T interceptTestFactoryMethod(Invocation<T> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        updateTestCaseResult(invocationContext, extensionContext);
        return invocation.proceed();
    }

    /**
     * Update test case
     *
     * Pass in params:
     * (at)ParameterizedTest
     * (at)ValueSource(strings = { "value 1", "value 2", "value 3" })
     * void paramTest(String param) {
     *     passingAssertionWith(param);
     * }
     */
    @Override
    public void interceptTestTemplateMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        updateTestCaseResult(invocationContext, extensionContext);
        invocation.proceed();
    }

    /** Update test case - Using (at)TestFactory??? Not tested. */
    @Override
    public void interceptDynamicTest(Invocation<Void> invocation, ExtensionContext extensionContext) throws Throwable {
        // no invocationContext???
        //updateTestCaseResult(invocationContext, extensionContext);
        invocation.proceed();
    }

    @Override
    public void interceptAfterEachMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        invocation.proceed();
    }

    @Override
    public void interceptAfterAllMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        invocation.proceed();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        getTestCaseResult(context).setStatus(PASSED);
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        getTestSuiteResult(context).startTestCase(context).setStatus(DISABLED);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        getTestCaseResult(context).setStatus(ABORTED).setCause(cause);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        getTestCaseResult(context).setStatus(FAILED).setCause(cause);
    }

    private void startReporting() {
        if (!isReporting) {
            reportWriter.prepare();
        }
        isReporting = true;
    }

    public void writeIndex(Report report, VersionInfo versionInfo) {
        if (isReporting) {
            reportWriter.writeStart();
            reportWriter.write(report, versionInfo);
        }
    }

    public TestResults getTestResults() {
        return testResults;
    }

    public TestSuiteResult getTestSuiteResult(ExtensionContext context) {
        return testResults.getTestResultsForClass(context);
    }

    public TestCaseResult getTestCaseResult(ExtensionContext context) {
        TestSuiteResult testSuiteResult = getTestSuiteResult(context);
        return testSuiteResult.getTestCaseResult(context);
    }

    /**
     * Implementation of update test case.
     *
     * @param invocationContext - This is later in the life cycle. getArguments() etc...
     * @param extensionContext - we have this at the start of the life cycle. getDisplayName() etc...
     */
    public void updateTestCaseResult(ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) {
        TestCaseResult testCaseResult = getTestCaseResult(extensionContext);
//        List<Object> arguments = invocationContext.getArguments();
//        Method method = invocationContext.getExecutable();
//        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
//        String displayName = extensionContext.getDisplayName();

        // TODO logic should really be inside testResults!
        testCaseResult.setWordify(wordifyExtensionContext.wordify(extensionContext, invocationContext.getArguments()).orElse(null));
        testCaseResult.setArgs(invocationContext.getArguments());
        setTestCaseName(invocationContext, extensionContext, testCaseResult);
    }

    private void setTestCaseName(ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext, TestCaseResult testCaseResult) {
        if (!extensionContext.getTestMethod().isPresent()) {
            return;
        }
        final Method method = extensionContext.getTestMethod().get();
        final ParameterizedTest parameterizedTest = method.getAnnotation(ParameterizedTest.class);
        testCaseResult.setName(method.getName());
        if (parameterizedTest != null) {
            String testName = parameterizedTest.name();
            // the default pattern doesn't work well it shows "[0] value 1"
            if (testName.equals("[{index}] {argumentsWithNames}") || extensionContext.getDisplayName().startsWith("[")) {
                String methodNameWordify = upperCaseFirstChar(wordifyMethodOrFieldName(method.getName()));
                testCaseResult.setDisplayName(testCaseNameFactory.createName(methodNameWordify, testCaseResult.getArgs()));
            } else {
                testCaseResult.setDisplayName(extensionContext.getDisplayName());
            }
        } else {
            testCaseResult.setDisplayName(upperCaseFirstChar(wordifyMethodOrFieldName(method.getName())));
        }
    }

    public void reset() {
        testResults.reset();
    }
}
