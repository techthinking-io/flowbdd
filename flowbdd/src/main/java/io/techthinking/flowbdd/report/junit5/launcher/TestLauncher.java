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

package io.techthinking.flowbdd.report.junit5.launcher;

import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod;

/**
 * Wrapper for selecting and launching JUnit tests.
 */
public class TestLauncher {

    public static void launch(Class<?> clazz) {
        DiscoverySelector selector = selectClass(clazz);
        launch(selector);
    }

    public static void launch(Class<?> clazz, String methodName) {
        DiscoverySelector selector = selectMethod(clazz, methodName);
        launch(selector);
    }

    public static void launch(DiscoverySelector... selectors) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request().selectors(selectors)
           // this would only work if we launched via this Launcher
           // .configurationParameter("junit.platform.output.capture.stdout", "true")
           // .configurationParameter("junit.platform.output.capture.stderr", "true")
            .build();

        Launcher launcher = LauncherFactory.create();
        launcher.execute(request);
    }
}
