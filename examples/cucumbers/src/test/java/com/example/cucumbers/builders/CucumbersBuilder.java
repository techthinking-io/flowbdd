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
import com.flowbdd.bdd.report.utils.BuilderUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public final class CucumbersBuilder implements Builder<List<Cucumber>> {
    private final List<CucumberBuilder> cucumbers = new ArrayList<>();

    private CucumbersBuilder() {
    }

    public static CucumbersBuilder cucumbers() {
        return new CucumbersBuilder();
    }

    public CucumbersBuilder with(CucumberBuilder... cucumbers) {
        this.cucumbers.clear();
        this.cucumbers.addAll(asList(cucumbers));
        return this;
    }

    public CucumbersBuilder with(List<CucumberBuilder> cucumbers) {
        this.cucumbers.clear();
        this.cucumbers.addAll(cucumbers);
        return this;
    }

    public CucumbersBuilder with(CucumberBuilder cucumber) {
        this.cucumbers.add(cucumber);
        return this;
    }

    public List<Cucumber> build() {
        return BuilderUtils.build(cucumbers);
    }
}
