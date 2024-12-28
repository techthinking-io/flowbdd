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

package io.bitsmart.bdd.report.report.model.builders;

import io.bitsmart.bdd.report.report.model.Method;
import io.bitsmart.bdd.report.utils.Builder;
import io.bitsmart.bdd.report.utils.BuilderUtils;

import java.util.ArrayList;
import java.util.List;

public final class MethodBuilder implements Builder<Method> {
    private String name;
    private String wordify;
    private List<ArgumentBuilder> arguments = new ArrayList<>();

    private MethodBuilder() {
    }

    public static MethodBuilder aMethod() {
        return new MethodBuilder();
    }

    public MethodBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MethodBuilder withWordify(String wordify) {
        this.wordify = wordify;
        return this;
    }

    public MethodBuilder withArguments(List<ArgumentBuilder> arguments) {
        this.arguments = arguments;
        return this;
    }

    public MethodBuilder withArgument(ArgumentBuilder argument) {
        this.arguments.add(argument);
        return this;
    }

    public Method build() {
        return new Method(name, wordify,  BuilderUtils.build(arguments));
    }
}
