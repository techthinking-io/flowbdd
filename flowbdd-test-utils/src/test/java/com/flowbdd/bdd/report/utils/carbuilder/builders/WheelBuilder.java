/*
 * Smart BDD - The smart way to do behavior-driven development.
 * Copyright (C)  2024  James Bayliss
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

package com.flowbdd.bdd.report.utils.carbuilder.builders;

import com.flowbdd.bdd.report.utils.Builder;
import com.flowbdd.bdd.report.utils.carbuilder.model.Wheel;

public final class WheelBuilder implements Builder<Wheel> {
    int size;

    private WheelBuilder() {
    }

    public static WheelBuilder aWheel() {
        return new WheelBuilder();
    }

    public WheelBuilder withSize(int size) {
        this.size = size;
        return this;
    }

    public Wheel build() {
        return new Wheel(size);
    }
}
