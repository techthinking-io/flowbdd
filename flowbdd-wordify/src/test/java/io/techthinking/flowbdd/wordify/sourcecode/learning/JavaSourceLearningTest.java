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

package io.techthinking.flowbdd.wordify.sourcecode.learning;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaSource;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.System.getProperty;
import static org.assertj.core.api.Assertions.assertThat;

class JavaSourceLearningTest {
    private static final String CLASS_NAME = "JavaSourceLearningTest";
    private static final String QUALIFIED_CLASS_NAME = "io.techthinking.flowbdd.wordify.sourcecode.learning.JavaSourceLearningTest";
    private static final String QUALIFIED_CLASS_PATH = "io/techthinking/flowbdd/wordify/sourcecode/learning/JavaSourceLearningTest.java";

    /**
     * https://www.baeldung.com/java-list-directory-files
     **/
    @Test
    void listDirectories() throws IOException {
        Set<String> dirs = listDirectories(workingDirectory().getPath());
        assertThat(dirs).isNotNull();
    }

    /**
     * src/test/java/
     */
    @Test
    void getSrcDir() {
        File file = new File(workingDirectory() + File.separator
            + "src" + File.separator
            + "test" + File.separator
            + "java" + File.separator);
        assertThat(file).exists();

        Class<? extends JavaSourceLearningTest> clazz = this.getClass();
        String relateSource = clazz.getName().replace('.', File.separatorChar) + ".java";

        File javaSourceFile = new File(file.getAbsolutePath() + File.separator + relateSource);
        assertThat(javaSourceFile).exists();
    }

    @Test
    void sourceForClass() throws IOException {
        File javaSourceFile = sourceFor(this.getClass());
        assertThat(javaSourceFile).exists();

        JavaProjectBuilder builder = new JavaProjectBuilder();
        JavaSource javaSource = builder.addSource(javaSourceFile);
        assertThat(javaSource).isNotNull();

        JavaClass javaClass = builder.getClassByName(this.getClass().getName());
        assertThat(javaClass).isNotNull();

        List<JavaMethod> methods = javaClass.getMethods();
        assertThat(methods).isNotNull();

        List<JavaMethod> matchedMethods = methods.stream().filter(method -> method.getName().endsWith("sourceForClass")).collect(Collectors.toList());
        assertThat(matchedMethods).isNotEmpty();

        JavaMethod method = matchedMethods.get(0);
        assertThat(method).isNotNull();
    }

    private File sourceFor(Class<?> clazz) {
        File file = new File(workingDirectory() + File.separator
            + "src" + File.separator
            + "test" + File.separator
            + "java" + File.separator);
        String relateSource = clazz.getName().replace('.', File.separatorChar) + ".java";

        return new File(file.getAbsolutePath() + File.separator + relateSource);
    }

    @Test
    void learningTestUserDir() {
        File file = workingDirectory();
        assertThat(file).exists();
        assertThat(file).isDirectory();
        assertThat(file).isAbsolute();
    }

    @Test
    void learningTestGetJavaSourceFromClassPath() {
        Class<? extends JavaSourceLearningTest> clazz = this.getClass();
        String fileLocation = clazz.getName().replace('.', File.separatorChar) + ".java";
        assertThat(fileLocation).isEqualTo(QUALIFIED_CLASS_PATH);

        URL resource = clazz.getClassLoader().getResource(fileLocation);
        assertThat(resource).isNull();
    }

    /**
     * This is very brittle because gradle and Intellij Idea junit will put the results in different places
     */
    @Test
    void learningTestForClassMethods() {
        Class<? extends JavaSourceLearningTest> clazz = this.getClass();
        assertThat(clazz.getSimpleName()).isEqualTo(CLASS_NAME);
        assertThat(clazz.getName()).isEqualTo(QUALIFIED_CLASS_NAME);
        assertThat(clazz.getTypeName()).isEqualTo(QUALIFIED_CLASS_NAME);
        assertThat(clazz.getCanonicalName()).isEqualTo(QUALIFIED_CLASS_NAME);

        CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
        String path = codeSource.getLocation().getPath();
        assertThat(path.endsWith("/build/classes/java/test/") || path.endsWith("/out/test/classes/")).isTrue();
    }

    private Set<String> listDirectories(String dir) throws IOException {
        Set<String> directories = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (Files.isDirectory(path)) {
                    directories.add(path.getFileName().toString());
                }
            }
        }
        return directories;
    }

    public static File workingDirectory() {
        return new File(getProperty("user.dir"));
    }
}