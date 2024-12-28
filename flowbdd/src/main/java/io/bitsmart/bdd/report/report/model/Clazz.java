/*
 * Smart BDD - The smart way to do behavior-driven development.
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

package io.bitsmart.bdd.report.report.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Clazz {
    private final String fullyQualifiedName;
    private final String className;
    private final String packageName;

    @JsonCreator
    public Clazz(
        @JsonProperty("fullyQualifiedName") String fullyQualifiedName,
        @JsonProperty("className") String className,
        @JsonProperty("packageName") String packageName) {
        this.fullyQualifiedName = fullyQualifiedName;
        this.className = className;
        this.packageName = packageName;
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    public String getClassName() {
        return className;
    }

    public String getPackageName() {
        return packageName;
    }

    public static Clazz testSuiteClass(Class<?> clazz) {
        return new Clazz(clazz.getName(), clazz.getSimpleName(), clazz.getPackage().getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clazz)) return false;
        Clazz clazz = (Clazz) o;
        return Objects.equals(fullyQualifiedName, clazz.fullyQualifiedName) && Objects.equals(className, clazz.className) && Objects.equals(packageName, clazz.packageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullyQualifiedName, className, packageName);
    }

    @Override
    public String toString() {
        return "Clazz{" +
            "fullyQualifiedName='" + fullyQualifiedName + '\'' +
            ", className='" + className + '\'' +
            ", packageName='" + packageName + '\'' +
            '}';
    }


}
