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

package io.techthinking.flowbdd.report.junit5.results.model;

import java.util.Objects;

public class ClassSimpleName {
    private final String value;

    private ClassSimpleName(String value) {
        this.value = value;
    }

    public static ClassSimpleName classSimpleName(Class clazz) {
        return new ClassSimpleName(clazz.getSimpleName());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassSimpleName)) return false;
        ClassSimpleName classSimpleName = (ClassSimpleName) o;
        return Objects.equals(value, classSimpleName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ClassName{" +
            "value='" + value + '\'' +
            '}';
    }
}
