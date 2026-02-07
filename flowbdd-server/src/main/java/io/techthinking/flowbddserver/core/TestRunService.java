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

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.Collectors;

@Service
public class TestRunService {

    private volatile DataReportIndex lastRun;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public synchronized DataReportIndex runTests(RunRequest request) {
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        Set<Path> roots = computeClasspathRoots();
        ClassLoader testClassLoader = createClassLoader(roots);
        Launcher launcher = LauncherFactory.create();

        List<DiscoverySelector> selectors = new ArrayList<>();
        if (request.getClassName() != null && !request.getClassName().isEmpty()) {
            String className = resolveClassName(request.getClassName());
            if (request.getMethodName() != null && !request.getMethodName().isEmpty()) {
                selectors.add(DiscoverySelectors.selectMethod(className, request.getMethodName()));
            } else {
                selectors.add(DiscoverySelectors.selectClass(className));
            }
        }

        LauncherDiscoveryRequestBuilder ldr = LauncherDiscoveryRequestBuilder.request();
        // Ensure we use our custom class loader for discovery
        ldr.configurationParameter("junit.platform.launcher.interceptors.enabled", "false");

        if (!selectors.isEmpty()) {
            ldr.selectors(selectors);
        } else {
            // No explicit selectors provided -> attempt to run everything on classpath roots
            if (!roots.isEmpty()) {
                ldr.selectors(DiscoverySelectors.selectClasspathRoots(roots));
            }
        }

        if (request.getTags() != null && !request.getTags().isEmpty()) {
            ldr.filters(TagFilter.includeTags(request.getTags().toArray(new String[0])));
        }

        LauncherDiscoveryRequest discoveryRequest = ldr.build();
        
        // Use a custom thread context classloader during execution to ensure tests can load their classes
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(testClassLoader);
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
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }

        lastRun = loadIndexJson();
        return lastRun;
    }

    protected String resolveClassName(String className) {
        Set<Path> roots = computeClasspathRoots();
        ClassLoader testClassLoader = createClassLoader(roots);

        if (className.contains(".")) {
            // Check if class is actually loadable
            try {
                Class.forName(className, false, testClassLoader);
                return className;
            } catch (ClassNotFoundException e) {
                // Not on classpath, but maybe it's in one of our roots
            }
        }
        
        // Try to find the class in the computed roots
        for (Path root : roots) {
            try (Stream<Path> walk = Files.walk(root)) {
                String targetFileName = className.replace(".", "/") + ".class";
                Optional<String> found = walk
                        .filter(Files::isRegularFile)
                        .map(p -> root.relativize(p).toString())
                        .filter(s -> s.endsWith(targetFileName) || s.endsWith(className + ".class"))
                        .map(s -> s.replace(".class", "").replace("/", ".").replace("\\", "."))
                        // If it ends with className but has a package, we prefer it
                        .findFirst();
                if (found.isPresent()) {
                    return found.get();
                }
            } catch (Exception ignored) {
            }
        }
        return className;
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

    private ClassLoader createClassLoader(Set<Path> roots) {
        try {
            List<URL> urls = new ArrayList<>();
            for (Path root : roots) {
                urls.add(root.toUri().toURL());
            }
            return new URLClassLoader(urls.toArray(new URL[0]), getClass().getClassLoader());
        } catch (Exception e) {
            return getClass().getClassLoader();
        }
    }

    private Set<Path> computeClasspathRoots() {
        Set<Path> roots = new java.util.HashSet<>();
        
        // 1. Standard classpath
        String cp = System.getProperty("java.class.path", "");
        if (!cp.isEmpty()) {
            String[] parts = cp.split(System.getProperty("path.separator"));
            java.util.Arrays.stream(parts)
                    .map(Paths::get)
                    .filter(p -> {
                        try { return Files.exists(p); } catch (Exception e) { return false; }
                    })
                    .forEach(roots::add);
        }

        // 2. Common Gradle/Maven test output directories (if they exist relative to current dir)
        String[] commonTestDirs = {
            "build/classes/java/test",
            "build/classes/kotlin/test",
            "target/test-classes",
            "examples/devteam/build/classes/java/test", // Specific to the issue reported
            "examples/devteam/out/test/classes"         // IntelliJ output dir
        };

        for (String dir : commonTestDirs) {
            Path p = Paths.get(dir);
            if (Files.exists(p) && Files.isDirectory(p)) {
                roots.add(p);
            }
        }
        
        return roots;
    }
}
