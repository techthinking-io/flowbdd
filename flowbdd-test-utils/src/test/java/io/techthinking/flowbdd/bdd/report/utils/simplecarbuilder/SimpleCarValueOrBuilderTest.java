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

package io.techthinking.flowbdd.bdd.report.utils.simplecarbuilder;

import io.techthinking.flowbdd.bdd.report.utils.simplecarbuilder.model.EngineType;
import io.techthinking.flowbdd.bdd.report.utils.simplecarbuilder.model.SimpleCar;
import io.techthinking.flowbdd.bdd.report.utils.simplecarbuilder.model.SimpleEngine;
import io.techthinking.flowbdd.bdd.report.utils.simplecarbuilder.valuebuilders.SimpleCarBuilder;
import org.junit.jupiter.api.Test;

import static io.techthinking.flowbdd.bdd.report.utils.simplecarbuilder.valuebuilders.SimpleCarBuilder.*;
import static io.techthinking.flowbdd.bdd.report.utils.simplecarbuilder.valuebuilders.SimpleEngineBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleCarValueOrBuilderTest {
    private static final EngineType ENGINE_TYPE = EngineType.PETROL;
    private static final int ENGINE_SIZE = 1;

    @Test
    void buildACarUsingValues() {
        SimpleCar car = aCar()
            .withEngine(anEngine()
                .withType(ENGINE_TYPE)
                .withSize(ENGINE_SIZE)
                .build())
            .build();

        assertThat(car.getEngine()).isEqualTo(new SimpleEngine(ENGINE_TYPE, ENGINE_SIZE));
    }

    @Test
    void buildACarUsingBuilders() {
        SimpleCar car = aCar()
            .withEngine(anEngine()
                .withType(ENGINE_TYPE)
                .withSize(ENGINE_SIZE))
            .build();

        assertThat(car.getEngine()).isEqualTo(new SimpleEngine(ENGINE_TYPE, ENGINE_SIZE));
    }

    /**
     * There's no good way to access the engine and modify it.
     * We don't know if the engine is a value or a builder.
     * If everything was a builder then we wouldn't have this issue.
     */
    @Test
    void updatingTheCarEngineRequiresANewEngine() {
        int anotherEngineSize = ENGINE_SIZE + 1;

        SimpleCarBuilder carBuilder = aCar()
            .withEngine(anEngine()
                .withType(ENGINE_TYPE)
                .withSize(ENGINE_SIZE));

        SimpleCar car = carBuilder.withEngine(anEngine()
            .withType(ENGINE_TYPE)
            .withSize(anotherEngineSize))
            .build();

        assertThat(car.getEngine()).isEqualTo(new SimpleEngine(ENGINE_TYPE, anotherEngineSize));
    }
}

