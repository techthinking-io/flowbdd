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

package io.techthinking.flowbdd.wordify;

import io.techthinking.flowbdd.wordify.sourcecode.JavaSourceWrapper;
import io.techthinking.flowbdd.wordify.sourcecode.MethodWrapper;
import io.techthinking.flowbdd.wordify.sourcecode.ParameterWrapper;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class MethodExtractor {
    private static final Logger logger = Logger.getLogger(MethodExtractor.class.getName());

    public MethodWrapper methodWrapper(Class<?> clazz, String methodName) {
        return methodWrapper(clazz, methodName, emptyList());
    }

    public MethodWrapper methodWrapper(Class<?> clazz, String methodName, List<Object> parameterValues) {
        try {
            JavaSourceWrapper sourceWrapper = new JavaSourceWrapper(clazz);
            List<JavaMethod> allMethods = sourceWrapper.getMethods();
            List<JavaMethod> matchedMethods = allMethods.stream().filter(m -> m.getName().contains(methodName)).collect(toList());
            JavaMethod matchedMethod = matchedMethods.get(0);
            String sourceCode = matchedMethod.getSourceCode();
            List<JavaParameter> parameters = sourceWrapper.getParams(matchedMethod.getName());
            return new MethodWrapper(sourceCode, parameterWrappers(parameters, parameterValues));
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not load Java source", e);
            return new MethodWrapper("Could not load Java source: " + e, emptyList());
        }
    }

    private List<ParameterWrapper> parameterWrappers(List<JavaParameter> parameters, List<Object> parameterValues) {
        List<ParameterWrapper> parameterWrappers = new ArrayList<>();
        try {
            int index = 0;
            for (JavaParameter param : parameters) {
                parameterWrappers.add(new ParameterWrapper(param, parameterValues.get(index)));
                index++;
            }
            return parameterWrappers;
        } catch (Exception e) {
            System.out.println("ParameterWrapper Error - parameters: " + parameters + ", parameterValues: " + parameterValues);
            return parameterWrappers;
        }
    }
}
