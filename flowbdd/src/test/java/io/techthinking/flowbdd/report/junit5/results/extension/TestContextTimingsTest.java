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
import io.techthinking.flowbdd.report.junit5.results.model.TestSuiteResult;
import io.techthinking.flowbdd.report.junit5.results.model.Timings;
import io.techthinking.flowbdd.report.report.writers.ReportWriter;
import io.techthinking.flowbdd.wordify.WordifyExtensionContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor.Invocation;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import java.lang.reflect.Method;
import java.time.Clock;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TestContextTimingsTest {

    private final TestResults testResults = mock(TestResults.class);
    private final WordifyExtensionContext wordifyExtensionContext = mock(WordifyExtensionContext.class);
    private final TestCaseNameFactory testCaseNameFactory = mock(TestCaseNameFactory.class);
    private final ReportWriter reportWriter = mock(ReportWriter.class);
    private final Clock clock = mock(Clock.class);

    private final TestContext testContext = new TestContext(
        testResults,
        wordifyExtensionContext,
        testCaseNameFactory,
        reportWriter,
        clock
    );

    @Test
    void shouldCaptureTimings() throws Throwable {
        ExtensionContext extensionContext = mock(ExtensionContext.class);
        TestSuiteResult testSuiteResult = mock(TestSuiteResult.class);
        TestCaseResult testCaseResult = mock(TestCaseResult.class);
        Timings timings = new Timings();
        
        when(extensionContext.getRequiredTestMethod()).thenReturn(this.getClass().getDeclaredMethods()[0]);
        ExtensionContext.Store store = mock(ExtensionContext.Store.class);
        when(extensionContext.getStore(any())).thenReturn(store);
        
        when(extensionContext.getStore(any())).thenReturn(store);
        
        when(store.remove("beforeEachStartTime", long.class)).thenReturn(100L);
        when(store.remove("underTestStartTime", long.class)).thenReturn(200L);
        when(store.remove("afterEachStartTime", long.class)).thenReturn(400L);
        
        when(testResults.getTestResultsForClass(extensionContext)).thenReturn(testSuiteResult);
        when(testSuiteResult.getTestCaseResult(extensionContext)).thenReturn(testCaseResult);
        when(testCaseResult.getTimings()).thenReturn(timings);
        when(wordifyExtensionContext.wordify(any(), any())).thenReturn(Optional.empty());

        Invocation<Void> invocation = mock(Invocation.class);
        ReflectiveInvocationContext<Method> reflectiveInvocationContext = mock(ReflectiveInvocationContext.class);
        when(reflectiveInvocationContext.getArguments()).thenReturn(Collections.emptyList());

        // Mock clock behavior
        when(clock.millis())
            .thenReturn(100L)  // beforeEach start
            .thenReturn(150L)  // beforeEach end
            .thenReturn(200L)  // underTest start
            .thenReturn(350L)  // underTest end
            .thenReturn(400L)  // afterEach start
            .thenReturn(475L); // afterEach end

        // Execute life cycle
        testContext.interceptBeforeEachMethod(invocation, reflectiveInvocationContext, extensionContext);
        testContext.interceptTestMethod(invocation, reflectiveInvocationContext, extensionContext);
        testContext.interceptAfterEachMethod(invocation, reflectiveInvocationContext, extensionContext);

        // Verify timings
        assertEquals(50L, timings.getBeforeEach());
        assertEquals(150L, timings.getUnderTest());
        assertEquals(75L, timings.getAfterEach());
        assertEquals(275L, timings.getTotal());
    }
}
