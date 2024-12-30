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

package component.report.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowbdd.report.report.filehandling.FileRepository;
import com.flowbdd.report.report.model.DataReportIndex;
import com.flowbdd.report.report.model.TestSuite;

import java.io.IOException;
import java.nio.file.Path;

public class ReportLoadFileUtils {
    private final FileRepository fileRepository = new FileRepository();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public TestSuite loadTestSuite(Path path) throws IOException {
        return MAPPER.readValue(fileRepository.read(path), TestSuite.class);
    }

    public DataReportIndex loadReportIndex(Path path) throws IOException {
        String contents = fileRepository.read(path);
        return MAPPER.readValue(contents, DataReportIndex.class);
    }
}

