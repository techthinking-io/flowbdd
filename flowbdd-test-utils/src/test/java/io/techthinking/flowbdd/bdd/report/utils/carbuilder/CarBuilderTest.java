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

package io.techthinking.flowbdd.bdd.report.utils.carbuilder;

import io.techthinking.flowbdd.bdd.report.utils.carbuilder.builders.CarBuilder;
import io.techthinking.flowbdd.bdd.report.utils.carbuilder.builders.SimpleEngineBuilder;
import io.techthinking.flowbdd.bdd.report.utils.carbuilder.builders.WheelBuilder;
import io.techthinking.flowbdd.bdd.report.utils.carbuilder.model.Car;
import io.techthinking.flowbdd.bdd.report.utils.carbuilder.model.EngineType;
import io.techthinking.flowbdd.bdd.report.utils.carbuilder.model.SimpleEngine;
import io.techthinking.flowbdd.bdd.report.utils.carbuilder.model.Wheel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.techthinking.flowbdd.bdd.report.utils.carbuilder.builders.CarBuilder.aCar;
import static io.techthinking.flowbdd.bdd.report.utils.carbuilder.builders.SimpleEngineBuilder.*;

class CarBuilderTest {
    private static final EngineType ENGINE_TYPE = EngineType.PETROL;
    private static final int ENGINE_SIZE = 1;
    private static final int WHEEL_SIZE = 1;

    @Test
    void buildCarUsingBuilders() {
        Car car = aDefaultCar().build();
        Assertions.assertThat(car.getEngine()).isEqualTo(new SimpleEngine(ENGINE_TYPE, ENGINE_SIZE));
    }

    @Test
    void updatingTheCarEngineAndWheels() {
        int anotherEngineSize = ENGINE_SIZE + 1;
        int anotherWheelSize = ENGINE_SIZE + 1;
        CarBuilder carBuilder = aDefaultCar();
        carBuilder.updateEngine().withSize(anotherEngineSize);
        carBuilder.updateWheels().forEach(builder -> builder.withSize(anotherWheelSize));

        Assertions.assertThat(carBuilder.build()).isEqualTo(
            new Car(
                new SimpleEngine(ENGINE_TYPE, anotherEngineSize),
                Arrays.asList(
                    new Wheel(WHEEL_SIZE + 1),
                    new Wheel(WHEEL_SIZE + 1),
                    new Wheel(WHEEL_SIZE + 1),
                    new Wheel(WHEEL_SIZE + 1)
                )
            )
        );
    }

    private CarBuilder aDefaultCar() {
        return aCar()
            .withEngine(anEngine()
                .withType(ENGINE_TYPE)
                .withSize(ENGINE_SIZE))
            .withWheels(Arrays.asList(aDefaultWheel(), aDefaultWheel(), aDefaultWheel(), aDefaultWheel()));
    }

    private WheelBuilder aDefaultWheel() {
        return WheelBuilder.aWheel().withSize(WHEEL_SIZE);
    }
}

