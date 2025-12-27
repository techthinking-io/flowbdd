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

package io.techthinking.flowbdd.wordify.sourcecode;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.JavaSource;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.lang.System.getProperty;

public class JavaSourceWrapper {
    private final JavaProjectBuilder builder = new JavaProjectBuilder();
    private final File javaSourceFile;
    private final JavaSource javaSource;
    private final JavaClass javaClass;
    private final String DEFAULT_SRC_PATH = "src" + File.separatorChar + "test" + File.separatorChar + "java" + File.separatorChar;

    public JavaSourceWrapper(Class clazz) throws IOException {
        javaSourceFile = sourceFor(clazz);
        javaSource = builder.addSource(javaSourceFile);
        javaClass = builder.getClassByName(clazz.getName());
    }

    public List<JavaMethod> getMethods() {
        return javaClass.getMethods();
    }

    /**
     * TODO you'd have to match the number of params
     * TODO prototyping code not production ready
     * @param methodName
     * @return
     */
    public List<JavaParameter> getParams(String methodName) {
        Optional<JavaMethod> javaMethod = javaClass.getMethods().stream().filter(m -> m.getName().equals(methodName)).findFirst();
        if (javaMethod.isPresent()) {
            return javaMethod.get().getParameters();
        }
        return Collections.emptyList();
    }

    private static File workingDirectory() {
        return new File(getProperty("user.dir"));
    }

    private File sourceFor(Class<?> clazz) {
        String relateSource = clazz.getName().replace('.', File.separatorChar) + ".java";
        return new File(workingDirectory() + File.separator + DEFAULT_SRC_PATH + relateSource);
    }
}
