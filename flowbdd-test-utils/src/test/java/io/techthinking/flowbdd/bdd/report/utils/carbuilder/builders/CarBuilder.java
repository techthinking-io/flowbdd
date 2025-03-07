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

package io.techthinking.flowbdd.bdd.report.utils.carbuilder.builders;

import io.techthinking.flowbdd.bdd.report.utils.Builder;
import io.techthinking.flowbdd.bdd.report.utils.BuilderUtils;
import io.techthinking.flowbdd.bdd.report.utils.carbuilder.model.Car;

import java.util.ArrayList;
import java.util.List;

public final class CarBuilder implements Builder<Car> {
    SimpleEngineBuilder engine;

    //TODO list of List<WheelBuilder> or WheelsBuilder
    // List<WheelBuilder> doesn't handle nulls very well
    List<WheelBuilder> wheels = new ArrayList<>();

    private CarBuilder() {
    }

    public static CarBuilder aCar() {
        return new CarBuilder();
    }

    public CarBuilder withEngine(SimpleEngineBuilder engine) {
        this.engine = engine;
        return this;
    }

    public CarBuilder withWheels(List<WheelBuilder> wheels) {
        this.wheels = wheels;
        return this;
    }

    public List<WheelBuilder> updateWheels() {
        return wheels;
    }

    public SimpleEngineBuilder updateEngine() {
        return engine;
    }

    public Car build() {
        return new Car(engine.build(), BuilderUtils.build(wheels));
    }
}
