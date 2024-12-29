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

import com.thoughtworks.qdox.model.JavaParameter;

import java.util.Objects;

public class ParameterWrapper {
    private final JavaParameter parameter;
    private final Object value;

    public ParameterWrapper(JavaParameter parameter, Object value) {
        this.parameter = parameter;
        this.value = value;
    }

    public JavaParameter getParameter() {
        return parameter;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParameterWrapper)) return false;
        ParameterWrapper that = (ParameterWrapper) o;
        return Objects.equals(parameter, that.parameter) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameter, value);
    }

    @Override
    public String toString() {
        return "ParameterWrapper{" +
            "parameter=" + parameter +
            ", value=" + value +
            '}';
    }
}
