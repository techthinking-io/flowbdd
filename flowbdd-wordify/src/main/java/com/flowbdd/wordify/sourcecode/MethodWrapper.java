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

package com.flowbdd.wordify.sourcecode;

import java.util.List;
import java.util.Objects;

public class MethodWrapper {
    private final String source;
    private final List<ParameterWrapper> parameters;

    public MethodWrapper(String source, List<ParameterWrapper> parameters) {
        this.source = source;
        this.parameters = parameters;
    }

    public String getSource() {
        return source;
    }

    public List<ParameterWrapper> getParameters() {
        return parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodWrapper)) return false;
        MethodWrapper that = (MethodWrapper) o;
        return Objects.equals(source, that.source) && Objects.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, parameters);
    }

    @Override
    public String toString() {
        return "MethodWrapper{" +
            "source='" + source + '\'' +
            ", parameters=" + parameters +
            '}';
    }
}
