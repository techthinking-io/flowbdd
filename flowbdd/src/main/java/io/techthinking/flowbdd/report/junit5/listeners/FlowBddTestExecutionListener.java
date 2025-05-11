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

package io.techthinking.flowbdd.report.junit5.listeners;

import io.techthinking.flowbdd.report.report.adapter.ReportFactory;
import io.techthinking.flowbdd.report.report.model.Report;
import io.techthinking.flowbdd.report.report.model.TestVersionInfoFactory;
import io.techthinking.flowbdd.report.report.model.VersionInfo;
import io.techthinking.flowbdd.report.junit5.results.extension.FlowBdd;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This has been added to resources/META-INF/services/
 */
public class FlowBddTestExecutionListener implements TestExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(FlowBddTestExecutionListener.class.getName());
    private final List<String> methodNames = new CopyOnWriteArrayList<>();

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        logger.debug("testPlanExecutionStarted: " + testPlan.containsTests() + ", roots: " + testPlan.getRoots());
        // init the Report?
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        logger.debug("testPlanExecutionFinished: " + methodNames + " " + testPlan.containsTests());
//        final Set<TestIdentifier> roots = testPlan.getRoots();
//        roots.forEach(root -> logger.debug("tags: " + root.getTags()));
//        roots.forEach(root -> logger.debug("source: " + root.getSource()));
//        roots.forEach(root -> logger.debug("id: " + root.getParentId()));
        final VersionInfo versionInfo = TestVersionInfoFactory.create(Clock.systemDefaultZone());
        final Report report = ReportFactory.create(FlowBdd.getTestContext().getTestResults(), versionInfo);
        FlowBdd.getTestContext().writeIndex(report, versionInfo);
    }

    @Override
    public void dynamicTestRegistered(TestIdentifier testIdentifier) {
    }

    @Override
    public void executionSkipped(TestIdentifier testIdentifier, String reason) {
    }

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        methodNames.add(testIdentifier.getDisplayName());
        //logger.info("executionFinished");
    }

    @Override
    public void reportingEntryPublished(TestIdentifier testIdentifier, ReportEntry entry) {
    }
}
