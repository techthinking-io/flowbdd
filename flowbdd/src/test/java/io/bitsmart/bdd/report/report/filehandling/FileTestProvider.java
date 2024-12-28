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

package io.bitsmart.bdd.report.report.filehandling;

/**
 * Source https://github.com/eugenp/tutorials/blob/master/testing-modules/mocks/src/test/java/com/baeldung/jimfs/FileTestProvider.java
 * */
public interface FileTestProvider {
    String FILE_CONTENT = "I'm the file content.";
    String FILE_NAME = "fileRepositoryRead.txt";
    String FILE_DIRECTORY = "data";
}