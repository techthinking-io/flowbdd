/*
 * Flow BDD - The productive way to test.
 * Copyright (C)  2022  James Bayliss
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

package io.techthinking.flowbdd.report.report.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Argument {
    private final Clazz clazz;
    private final String value;

    @JsonCreator
    public Argument(
        @JsonProperty("class") Clazz clazz,
        @JsonProperty("value") String value) {
        this.clazz = clazz;
        this.value = value;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Argument)) return false;
        Argument argument = (Argument) o;
        return Objects.equals(clazz, argument.clazz) && Objects.equals(value, argument.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, value);
    }

    @Override
    public String toString() {
        return "Argument{" +
            "clazz=" + clazz +
            ", value='" + value + '\'' +
            '}';
    }
}
