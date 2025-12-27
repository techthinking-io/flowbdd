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

package io.techthinking.flowbdd.report.report.model.builders;

import io.techthinking.flowbdd.report.report.model.Clazz;
import io.techthinking.flowbdd.bdd.report.utils.Builder;

public final class ClazzBuilder  implements Builder<Clazz> {
    private String fullyQualifiedName;
    private String className;
    private String packageName;

    private ClazzBuilder() {
    }

    public static ClazzBuilder aClazz() {
        return new ClazzBuilder();
    }

    public ClazzBuilder withFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
        return this;
    }

    public ClazzBuilder withClassName(String className) {
        this.className = className;
        return this;
    }

    public ClazzBuilder withPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public Clazz build() {
        return new Clazz(fullyQualifiedName, className, packageName);
    }
}
