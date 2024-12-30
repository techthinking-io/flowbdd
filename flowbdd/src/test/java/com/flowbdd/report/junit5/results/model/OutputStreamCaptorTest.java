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

package com.flowbdd.report.junit5.results.model;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(OrderAnnotation.class)
class OutputStreamCaptorTest {

    @Order(0)
    @Test
    void captureStandardOut() {
        OutputStreamCaptor outputStreamCaptor = new OutputStreamCaptor();
        outputStreamCaptor.start();

        System.out.println("out message 1");
        System.out.println("out message 2");

        assertThat(outputStreamCaptor.getSystemOut()).isEqualTo("out message 1\nout message 2\n");
    }

    @Order(1)
    @Test
    void captureStandardOutDoesNotHaveStaticState() {
        OutputStreamCaptor outputStreamCaptor = new OutputStreamCaptor();
        outputStreamCaptor.start();

        assertThat(outputStreamCaptor.getSystemOut()).isEmpty();

        System.out.println("out message 3");

        assertThat(outputStreamCaptor.getSystemOut()).isEqualTo("out message 3\n");
    }

    @Order(2)
    @Test
    void captureStandardErr() {
        OutputStreamCaptor outputStreamCaptor = new OutputStreamCaptor();
        outputStreamCaptor.start();

        System.err.println("err message 1");
        System.err.println("err message 2");

        assertThat(outputStreamCaptor.getSystemErr()).isEqualTo("err message 1\nerr message 2\n");
    }

    @Order(3)
    @Test
    void captureStandardOutAndErrAreSeparateStreams() {
        OutputStreamCaptor outputStreamCaptor = new OutputStreamCaptor();
        outputStreamCaptor.start();

        System.out.println("out message 1");
        System.err.println("err message 1");

        assertThat(outputStreamCaptor.getSystemOut()).isEqualTo("out message 1\n");
        assertThat(outputStreamCaptor.getSystemErr()).isEqualTo("err message 1\n");
    }

    @Disabled("stop capturing doesn't work as expected")
    @Order(4)
    @Test
    void captureStandardOutAndErrStopsCapturing() {
        OutputStreamCaptor outputStreamCaptor = new OutputStreamCaptor();
        outputStreamCaptor.start();

        System.out.println("out message 1");
        System.err.println("err message 1");

        outputStreamCaptor.stop();

        System.out.println("out message 2");
        System.err.println("err message 2");

        assertThat(outputStreamCaptor.getSystemOut()).isEqualTo("out message 1\n");
        assertThat(outputStreamCaptor.getSystemErr()).isEqualTo("err message 1\n");
    }
}