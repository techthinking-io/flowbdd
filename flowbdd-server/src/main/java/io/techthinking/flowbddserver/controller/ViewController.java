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

package io.techthinking.flowbddserver.controller;

import io.techthinking.flowbdd.report.report.model.DataReportIndex;
import io.techthinking.flowbdd.report.report.model.TestSuite;
import io.techthinking.flowbdd.report.report.model.TestSuiteNameToFile;
import io.techthinking.flowbdd.report.report.model.TestVersionInfoFactory;
import io.techthinking.flowbdd.report.report.model.VersionInfo;
import io.techthinking.flowbddserver.api.ReportController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewController {

    private final ReportController reportController;

    public ViewController(ReportController reportController) {
        this.reportController = reportController;
    }

    @GetMapping({"/", "/index.html"})
    public String index(Model model) {
        try {
            DataReportIndex index = reportController.getIndex();
            List<TestSuiteNameToFile> links = index.getLinks().getTestSuites().stream()
                .map(l -> new TestSuiteNameToFile(l.getName(), l.getFile().replace(".json", "")))
                .collect(Collectors.toList());

            VersionInfo versionInfo = TestVersionInfoFactory.create(Clock.systemDefaultZone());

            model.addAttribute("dataReportIndex", index);
            model.addAttribute("summary", index.getSummary());
            model.addAttribute("links", links);
            model.addAttribute("versionInfo", versionInfo);
            model.addAttribute("isServer", true);
            return "index";
        } catch (Exception e) {
            return "redirect:/runner/index.html";
        }
    }

    @GetMapping("/suite/{fileName}")
    public String testSuite(@PathVariable String fileName, Model model) {
        TestSuite testSuite = reportController.getTestSuite(fileName);
        VersionInfo versionInfo = TestVersionInfoFactory.create(Clock.systemDefaultZone());

        model.addAttribute("testSuite", testSuite);
        model.addAttribute("testCases", testSuite.getTestCases());
        model.addAttribute("versionInfo", versionInfo);
        model.addAttribute("isServer", true);
        return "test-suite";
    }

    @GetMapping("/TEST-{className}")
    public String testSuiteByClass(@PathVariable String className, Model model) {
        TestSuite testSuite = reportController.getTestSuiteByClass(className);
        VersionInfo versionInfo = TestVersionInfoFactory.create(Clock.systemDefaultZone());

        model.addAttribute("testSuite", testSuite);
        model.addAttribute("testCases", testSuite.getTestCases());
        model.addAttribute("versionInfo", versionInfo);
        model.addAttribute("isServer", true);
        return "test-suite";
    }
}
