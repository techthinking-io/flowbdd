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

package io.techthinking.flowbddserver.core;

import io.techthinking.flowbddserver.api.RunRequest;
import io.techthinking.flowbdd.report.config.FlowBddConfig;
import io.techthinking.flowbdd.report.report.model.DataReportIndex;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.TagFilter;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.Collectors;

@Service
public class TestRunService {

    private volatile DataReportIndex lastRun;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public synchronized DataReportIndex runTests(RunRequest request) {
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        Launcher launcher = LauncherFactory.create();

        List<DiscoverySelector> selectors = new ArrayList<>();
        if (request.getClassName() != null && !request.getClassName().isEmpty()) {
            if (request.getMethodName() != null && !request.getMethodName().isEmpty()) {
                selectors.add(DiscoverySelectors.selectMethod(request.getClassName(), request.getMethodName()));
            } else {
                selectors.add(DiscoverySelectors.selectClass(request.getClassName()));
            }
        }

        LauncherDiscoveryRequestBuilder ldr = LauncherDiscoveryRequestBuilder.request();
        if (!selectors.isEmpty()) {
            ldr.selectors(selectors);
        } else {
            // No explicit selectors provided -> attempt to run everything on classpath
            Set<Path> roots = computeClasspathRoots();
            if (!roots.isEmpty()) {
                ldr.selectors(DiscoverySelectors.selectClasspathRoots(roots));
            }
        }

        if (request.getTags() != null && !request.getTags().isEmpty()) {
            ldr.filters(TagFilter.includeTags(request.getTags().toArray(new String[0])));
        }

        LauncherDiscoveryRequest discoveryRequest = ldr.build();
        launcher.registerTestExecutionListeners(listener);

        int remainingReruns = Math.max(0, request.getRerunCount());
        TestExecutionSummary summary;

        do {
            launcher.execute(discoveryRequest);
            summary = listener.getSummary();
            // If there were failures and reruns remain, rebuild request for failed tests only
            if (remainingReruns > 0 && summary.getFailures() != null && !summary.getFailures().isEmpty()) {
                List<DiscoverySelector> failedSelectors = summary.getFailures().stream()
                        .map(TestExecutionSummary.Failure::getTestIdentifier)
                        .map(TestIdentifier::getUniqueId)
                        .map(DiscoverySelectors::selectUniqueId)
                        .collect(Collectors.toList());
                if (!failedSelectors.isEmpty()) {
                    ldr = LauncherDiscoveryRequestBuilder.request();
                    ldr.selectors(failedSelectors);
                    discoveryRequest = ldr.build();
                    // reset listener for next run cycle
                    listener = new SummaryGeneratingListener();
                    launcher = LauncherFactory.create();
                    launcher.registerTestExecutionListeners(listener);
                } else {
                    break;
                }
            }
        } while (remainingReruns-- > 0 && !summary.getFailures().isEmpty());

        lastRun = loadIndexJson();
        return lastRun;
    }

    private DataReportIndex loadIndexJson() {
        try {
            Path indexPath = FlowBddConfig.getDataPath().resolve("index.json");
            if (Files.exists(indexPath)) {
                return objectMapper.readValue(indexPath.toFile(), DataReportIndex.class);
            }
        } catch (Exception ignored) { }
        return null;
    }

    public DataReportIndex getLastRun() {
        // Check if index.json was updated in the last 30 seconds
        try {
            Path indexPath = FlowBddConfig.getDataPath().resolve("index.json");
            if (Files.exists(indexPath)) {
                long lastModified = Files.getLastModifiedTime(indexPath).toMillis();
                long now = System.currentTimeMillis();
                if (now - lastModified < 30000) {
                    // It's fresh!
                    if (lastRun == null || 
                        Instant.parse(lastRun.getTimeStamp()).toEpochMilli() < lastModified) {
                        lastRun = objectMapper.readValue(indexPath.toFile(), DataReportIndex.class);
                    }
                }
            }
        } catch (Exception ignored) { }
        
        return lastRun;
    }

    private Set<Path> computeClasspathRoots() {
        String cp = System.getProperty("java.class.path", "");
        if (cp.isEmpty()) return Set.of();
        String[] parts = cp.split(System.getProperty("path.separator"));
        try (Stream<Path> stream = java.util.Arrays.stream(parts)
                .map(Paths::get)
                .filter(p -> {
                    try { return Files.exists(p); } catch (Exception e) { return false; }
                })) {
            return stream.collect(Collectors.toSet());
        }
    }
}
