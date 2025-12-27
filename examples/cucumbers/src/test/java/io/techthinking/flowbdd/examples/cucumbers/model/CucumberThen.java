/*
 * Flow BDD - The productive way to test.
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

package io.techthinking.flowbdd.examples.cucumbers.model;

import io.techthinking.flowbdd.examples.cucumbers.builders.CucumberBuilder;

import java.util.List;
import java.util.Optional;

public class CucumberThen {
    private final Integer quantity;
    private final String colour;
    private final List<CucumberBuilder> cucumbers;

    public CucumberThen(Integer quantity, String colour, List<CucumberBuilder> cucumbers) {
        this.quantity = quantity;
        this.colour = colour;
        this.cucumbers = cucumbers;
    }

    public Optional<Integer> getQuantity() {
        return Optional.ofNullable(quantity);
    }

    public Optional<String> getColour() {
        return Optional.ofNullable(colour);
    }

    public Optional<List<CucumberBuilder>> getCucumbers() {
        return Optional.ofNullable(cucumbers);
    }
}
