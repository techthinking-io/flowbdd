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

import com.example.cucumbers.model.CucumberThen;
import io.flowbdd.bdd.report.utils.ThenBuilder;

import java.util.List;

import static java.util.Arrays.asList;

public class CucumberThenBuilder implements ThenBuilder<CucumberThen> {
    private Integer quantity;
    private String colour;
    private List<CucumberBuilder> cucumbers;

    private CucumberThenBuilder() {
    }

    public static CucumberThenBuilder iShouldHaveCucumbers() {
        return new CucumberThenBuilder();
    }

    public static CucumberThenBuilder iShouldHave(CucumberBuilder... cucumbers) {
        return new CucumberThenBuilder().with(cucumbers);
    }

    public CucumberThenBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public CucumberThenBuilder withColour(String colour) {
        this.colour = colour;
        return this;
    }

    public CucumberThenBuilder with(CucumberBuilder... cucumbers) {
        this.cucumbers = asList(cucumbers);
        return this;
    }


    public CucumberThen build() {
        return new CucumberThen(quantity, colour, cucumbers);
    }


}
