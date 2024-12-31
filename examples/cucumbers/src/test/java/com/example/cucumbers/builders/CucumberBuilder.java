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

package com.example.cucumbers.builders;

import com.example.cucumbers.model.Cucumber;
import com.flowbdd.bdd.report.utils.Builder;

public final class CucumberBuilder implements Builder<Cucumber> {
    private String colour;

    private CucumberBuilder() {
    }

    public static CucumberBuilder aCucumber() {
        return new CucumberBuilder();
    }

    public static CucumberBuilder andACucumber() {
        return aCucumber();
    }

    public CucumberBuilder withColour(String colour) {
        this.colour = colour;
        return this;
    }

    public Cucumber build() {
        return new Cucumber(colour);
    }}
