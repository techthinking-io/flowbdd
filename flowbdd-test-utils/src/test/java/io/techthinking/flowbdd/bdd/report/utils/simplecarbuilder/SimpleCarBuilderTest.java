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

package io.techthinking.flowbdd.bdd.report.utils.simplecarbuilder;

import io.techthinking.flowbdd.bdd.report.utils.simplecarbuilder.builders.SimpleCarBuilder;
import io.techthinking.flowbdd.bdd.report.utils.simplecarbuilder.model.EngineType;
import io.techthinking.flowbdd.bdd.report.utils.simplecarbuilder.model.SimpleCar;
import io.techthinking.flowbdd.bdd.report.utils.simplecarbuilder.model.SimpleEngine;
import org.junit.jupiter.api.Test;

import static io.techthinking.flowbdd.bdd.report.utils.simplecarbuilder.builders.SimpleCarBuilder.aCar;
import static io.techthinking.flowbdd.bdd.report.utils.simplecarbuilder.builders.SimpleEngineBuilder.anEngine;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleCarBuilderTest {
    private static final EngineType ENGINE_TYPE = EngineType.PETROL;
    private static final int ENGINE_SIZE = 1;

    @Test
    void buildACarUsingBuilders() {
        SimpleCar car = aDefaultCar().build();
        assertThat(car.getEngine()).isEqualTo(new SimpleEngine(ENGINE_TYPE, ENGINE_SIZE));
    }

    /**
     * Address short fall from CarValueOrBuilderTest#updatingADefaultCarEngineRequiresANewEngine()
     */
    @Test
    void updatingTheCarEngine() {
        int anotherEngineSize = ENGINE_SIZE + 1;
        SimpleCarBuilder simpleCarBuilder = aDefaultCar();
        simpleCarBuilder.updateEngine().withSize(anotherEngineSize);

        assertThat(simpleCarBuilder.build()).isEqualTo(
            new SimpleCar(new SimpleEngine(ENGINE_TYPE, anotherEngineSize)));
    }

    private SimpleCarBuilder aDefaultCar() {
        return aCar()
            .withEngine(anEngine()
                .withType(ENGINE_TYPE)
                .withSize(ENGINE_SIZE));
    }
}

