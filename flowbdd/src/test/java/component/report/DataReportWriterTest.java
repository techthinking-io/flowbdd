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

package component.report;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import io.techthinking.flowbdd.report.report.model.builders.ClazzBuilder;
import io.techthinking.flowbdd.report.report.model.builders.MethodBuilder;
import io.techthinking.flowbdd.report.report.model.builders.ReportBuilder;
import io.techthinking.flowbdd.report.report.model.builders.ReportIndexBuilder;
import io.techthinking.flowbdd.report.report.model.builders.TestCaseBuilder;
import io.techthinking.flowbdd.report.report.model.builders.TestSuiteBuilder;
import io.techthinking.flowbdd.report.report.model.builders.TestSuiteSummaryBuilder;
import io.techthinking.flowbdd.report.report.model.builders.ThrowableBuilder;
import component.report.utils.ReportLoadFileUtils;
import io.techthinking.flowbdd.report.report.writers.FileNameProvider;
import io.techthinking.flowbdd.report.report.writers.DataReportWriter;
import io.techthinking.flowbdd.report.report.model.Report;
import io.techthinking.flowbdd.report.report.model.DataReportIndex;
import io.techthinking.flowbdd.report.report.model.Status;
import io.techthinking.flowbdd.report.report.model.TestSuite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static io.techthinking.flowbdd.report.report.model.builders.ClazzBuilder.aClazz;
import static io.techthinking.flowbdd.report.report.model.builders.MethodBuilder.aMethod;
import static io.techthinking.flowbdd.report.report.model.builders.ReportIndexBuilder.aReportIndex;
import static io.techthinking.flowbdd.report.report.model.builders.TestCaseBuilder.aTestCase;
import static io.techthinking.flowbdd.report.report.model.builders.TestSuiteBuilder.aTestSuite;
import static io.techthinking.flowbdd.report.report.model.builders.TestSuiteLinksBuilder.aTestSuiteLinks;
import static io.techthinking.flowbdd.report.report.model.builders.TestSuiteNameToFileBuilder.aTestSuiteNameToFile;
import static io.techthinking.flowbdd.report.report.model.builders.TestSuiteSummaryBuilder.aTestSuiteSummary;
import static io.techthinking.flowbdd.report.report.model.builders.ThrowableBuilder.aThrowable;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DataReportWriterTest {
    static final String DEFAULT_DATE_TIME = "2000-01-29T09:15:30.00Z";
    private final FileNameProvider dataFileNameProvider = mock(FileNameProvider.class);
    private final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix());
    private final ReportLoadFileUtils fileUtils = new ReportLoadFileUtils();
    private final Path dataPath = fileSystem.getPath("/data");
    private final Path testSuitePath = dataPath.resolve("testSuite.json");
    private final Path indexPath = dataPath.resolve("index.json");

    private final DataReportWriter dataReportWriter = new DataReportWriter(dataFileNameProvider);
    private final Report report = aDefaultReport().build();

    @BeforeEach
    void setUp() {
        when(dataFileNameProvider.path()).thenReturn(dataPath);
        when(dataFileNameProvider.file(any(TestSuite.class))).thenReturn(testSuitePath);
        when(dataFileNameProvider.indexFile()).thenReturn(indexPath);
    }

    @Test
    void prepareDataDirectory_deletesExistingDirectory() throws IOException {
        assertThat(dataPath).doesNotExist();
        Path testFile = dataPath.resolve("test.txt");

        Files.createDirectories(dataPath);
        Files.createFile(testFile);
        assertThat(testFile).exists();

        dataReportWriter.prepareDataDirectory();
        assertThat(testFile).doesNotExist();
        assertThat(dataPath).exists();
    }

    @Test
    void prepareDataDirectory_createsDirectory() {
        assertThat(dataPath).doesNotExist();

        dataReportWriter.prepareDataDirectory();
        assertThat(dataPath).exists();
    }

    @Test
    void writesReportIndex() throws IOException {
        dataReportWriter.prepareDataDirectory();
        dataReportWriter.write(report.getIndex());
        DataReportIndex loadedDataReportIndex = fileUtils.loadReportIndex(indexPath);
        assertThat(loadedDataReportIndex).isEqualTo(aDefaultReportIndex().build());
    }

    @Test
    void writesReportTestSuites() throws IOException {
        dataReportWriter.prepareDataDirectory();
        dataReportWriter.write(report.getTestSuites().get(0));
        TestSuite testSuite = fileUtils.loadTestSuite(testSuitePath);
        assertThat(testSuite).isEqualTo(aDefaultTestSuiteBuilder().build());
    }

    public static ReportBuilder aDefaultReport() {
        return ReportBuilder.aReport()
            .withReportIndex(aDefaultReportIndex())
            .withTestSuites(singletonList(aDefaultTestSuiteBuilder()))
            .withTestCases(singletonList(aDefaultTestCaseBuilder()))
            .withTimeStamp(DEFAULT_DATE_TIME);
    }

    public static TestSuiteBuilder aDefaultTestSuiteBuilder() {
        return aTestSuite()
            .withName("defaultName")
            .withClassName("defaultClassName")
            .withPackageName("defaultPackageName")
            .withTestCases(singletonList(aDefaultTestCase()))
            .withSummary(aDefaultSummary());
    }

    public static TestCaseBuilder aDefaultTestCaseBuilder() {
        return aTestCase()
            .withWordify("defaultWordify")
            .withStatus(Status.PASSED)
            .withCause(aDefaultThrowable())
            .withMethod(aDefaultMethod())
            .withClazz(aDefaultClazz())
//            .withNotes()    // TODO
//            .withTimings()  // TODO
            ;
    }

    public static MethodBuilder aDefaultMethod() {
        return aMethod()
            .withName("defaultMethodName")
            .withWordify("defaultWordify")
            .withArguments(emptyList());
    }

    public static ThrowableBuilder aDefaultThrowable() {
        return aThrowable()
            .withClazz(aDefaultClazz())
            .withMessage("defaultMessage")
            .withCause(null)
            .withStacktrace(new ArrayList<>());
    }

    public static ClazzBuilder aDefaultClazz() {
        return aClazz()
            .withClassName("defaultClassName")
            .withPackageName("defaultPackageName")
            .withFullyQualifiedName("defaultFullyQualifiedName");
    }

    public static TestSuiteSummaryBuilder aDefaultSummary() {
        return aTestSuiteSummary();
    }

    public static TestCaseBuilder aDefaultTestCase() {
        return aTestCase();
    }

    public static ReportIndexBuilder aDefaultReportIndex() {
        return aReportIndex()
            .withLinks(aTestSuiteLinks()
                .withTestSuites(singletonList(aTestSuiteNameToFile().withName("defaultName").withFile("defaultFileName"))))
            .withSummary(aTestSuiteSummary())
            .withTimeStamp(DEFAULT_DATE_TIME);
    }
}