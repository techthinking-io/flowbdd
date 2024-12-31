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

package com.flowbdd.bdd.report.utils.simplecarbuilder.valuebuilders;

import com.flowbdd.bdd.report.utils.Builder;
import com.flowbdd.bdd.report.utils.simplecarbuilder.model.EngineType;
import com.flowbdd.bdd.report.utils.simplecarbuilder.model.SimpleEngine;

public final class SimpleEngineBuilder implements Builder<SimpleEngine> {
    EngineType type;
    int size;

    private SimpleEngineBuilder() {
    }

    public static SimpleEngineBuilder anEngine() {
        return new SimpleEngineBuilder();
    }

    public SimpleEngineBuilder withType(EngineType type) {
        this.type = type;
        return this;
    }

    public SimpleEngineBuilder withSize(int size) {
        this.size = size;
        return this;
    }

    public SimpleEngine build() {
        return new SimpleEngine(type, size);
    }
}
