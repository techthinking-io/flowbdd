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

package com.flowbdd.bdd.report.utils;

import java.util.Optional;

public class ValueOrBuilder<T> {
    private final T value;
    private final Builder<T> builder;

    public ValueOrBuilder(T value) {
        this.value = value;
        this.builder = null;
    }

    public ValueOrBuilder(Builder<T> builder) {
        this.value = null;
        this.builder = builder;
    }

    public T get() {
        return Optional.ofNullable(value).orElseGet(this::build);
    }

    private T build() {
        return Optional.ofNullable(builder).map(Builder::build).orElse(null);
    }
}
