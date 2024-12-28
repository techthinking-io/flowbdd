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

import java.util.List;
import java.util.Objects;

public class Method {
    private final String name;
    private final String wordify;
    private final List<Argument> arguments;

    @JsonCreator
    public Method(
        @JsonProperty("name") String name,
        @JsonProperty("wordify") String wordify,
        @JsonProperty("arguments") List<Argument> arguments) {
        this.name = name;
        this.wordify = wordify;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public String getWordify() {
        return wordify;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Method)) return false;
        Method method = (Method) o;
        return Objects.equals(name, method.name) && Objects.equals(wordify, method.wordify) && Objects.equals(arguments, method.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, wordify, arguments);
    }

    @Override
    public String toString() {
        return "Method{" +
            "name='" + name + '\'' +
            ", wordify='" + wordify + '\'' +
            ", arguments=" + arguments +
            '}';
    }
}
