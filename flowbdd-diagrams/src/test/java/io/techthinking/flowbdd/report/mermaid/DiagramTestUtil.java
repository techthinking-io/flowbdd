/*
 * Flow BDD - The productive way to test.
 * Copyright (C)  2025  James Bayliss
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

package io.techthinking.flowbdd.report.mermaid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for writing Mermaid diagrams to files for manual validation.
 * The generated files can be viewed in IntelliJ IDEA with the Mermaid plugin.
 */
public class DiagramTestUtil {

    private static final String OUTPUT_DIR = "src/test/resources/generated-diagrams";
    private static final String REFERENCE_DIR = "src/test/resources/reference-diagrams";

    /**
     * Ensures the output directory exists.
     * @throws IOException if directory creation fails
     */
    public static void ensureOutputDirectoryExists() throws IOException {
        Path outputDirPath = Paths.get(OUTPUT_DIR);
        if (!Files.exists(outputDirPath)) {
            Files.createDirectories(outputDirPath);
        }
    }

    /**
     * Writes a diagram to a file in Markdown format.
     * 
     * @param diagramContent the content of the diagram
     * @param fileName the name of the file (without path)
     * @param title the title to use in the Markdown
     * @return the absolute path to the created file
     * @throws IOException if writing to the file fails
     */
    public static String writeDiagramToFile(String diagramContent, String fileName, String title) throws IOException {
        ensureOutputDirectoryExists();
        
        Path filePath = Paths.get(OUTPUT_DIR, fileName);
        
        // Wrap the content in Markdown format for Mermaid
        String markdownContent = "# " + title + "\n\n" +
                "```mermaid\n" +
                diagramContent + "\n" +
                "```\n";
        
        // Write to file
        Files.write(filePath, markdownContent.getBytes());
        
        return filePath.toAbsolutePath().toString();
    }

    public static String loadReferenceDiagram(String fileName) throws IOException {
        Path filePath = Paths.get(REFERENCE_DIR, fileName);
        return new String(Files.readAllBytes(filePath));
    }

    public static String loadOutputDiagram(String fileName) throws IOException {
        Path filePath = Paths.get(OUTPUT_DIR, fileName);
        return new String(Files.readAllBytes(filePath));
    }
}