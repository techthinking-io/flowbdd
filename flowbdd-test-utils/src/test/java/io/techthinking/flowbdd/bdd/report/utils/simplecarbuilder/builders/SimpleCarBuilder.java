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

package io.techthinking.flowbdd.bdd.report.utils.simplecarbuilder.builders;

import io.techthinking.flowbdd.bdd.report.utils.Builder;
import io.techthinking.flowbdd.bdd.report.utils.simplecarbuilder.model.SimpleCar;

public final class SimpleCarBuilder implements Builder<SimpleCar> {
    SimpleEngineBuilder engine;

    private SimpleCarBuilder() {
    }

    public static SimpleCarBuilder aCar() {
        return new SimpleCarBuilder();
    }

    public SimpleCarBuilder withEngine(SimpleEngineBuilder engine) {
        this.engine = engine;
        return this;
    }

    public SimpleEngineBuilder updateEngine() {
        return engine;
    }

    public SimpleCar build() {
        return new SimpleCar(engine.build());
    }
}
