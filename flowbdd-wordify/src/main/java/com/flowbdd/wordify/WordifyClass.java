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

package com.flowbdd.wordify;

import com.flowbdd.wordify.legacy.WordifyString;
import com.flowbdd.wordify.sourcecode.MethodWrapper;
import com.flowbdd.wordify.sourcecode.ParameterWrapper;

import java.util.List;
import java.util.logging.Logger;

import static java.util.Collections.emptyList;

public class WordifyClass {
    private static final Logger logger = Logger.getLogger(WordifyClass.class.getName());
    private final MethodExtractor methodExtractor = new MethodExtractor();

    public String wordify(Class<?> clazz, String methodName) {
        return wordify(clazz, methodName, emptyList());
    }

    public String wordify(Class<?> clazz, String methodName, List<Object> parameterValues) {
        MethodWrapper method = methodExtractor.methodWrapper(clazz, methodName, parameterValues);
        String sourceCode = updateSourceCode(method.getSource(), method.getParameters());
        return new WordifyString(sourceCode).wordify();
    }

    private String updateSourceCode(String sourceCode, List<ParameterWrapper> parameters) {
        for (ParameterWrapper parameter: parameters) {
            String value = parameter.getValue() == null ? "null" : parameter.getValue().toString();
            sourceCode = sourceCode.replace(parameter.getParameter().getName(), value);
        }
        return sourceCode;
    }
}
