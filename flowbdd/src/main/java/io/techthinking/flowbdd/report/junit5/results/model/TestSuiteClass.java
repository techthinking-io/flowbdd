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

package io.techthinking.flowbdd.report.junit5.results.model;

import java.util.Objects;

public class TestSuiteClass {
    private final String fullyQualifiedName;
    private final String className;
    private final String packageName;

    private TestSuiteClass(String fullyQualifiedName, String className, String packageName) {
        this.fullyQualifiedName = fullyQualifiedName;
        this.className = className;
        this.packageName = packageName;
    }

    public static TestSuiteClass testSuiteClass(Class<?> clazz) {
        return new TestSuiteClass(clazz.getName(), clazz.getSimpleName(), clazz.getPackage().getName());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestSuiteClass)) return false;
        TestSuiteClass that = (TestSuiteClass) o;
        return Objects.equals(fullyQualifiedName, that.fullyQualifiedName) && Objects.equals(className, that.className) && Objects.equals(packageName, that.packageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullyQualifiedName, className, packageName);
    }

    @Override
    public String toString() {
        return "TestSuiteResultsId{" +
            "name='" + fullyQualifiedName + '\'' +
            ", className='" + className + '\'' +
            ", packageName='" + packageName + '\'' +
            '}';
    }
}
