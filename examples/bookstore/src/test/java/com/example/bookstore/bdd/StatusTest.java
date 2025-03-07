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

package com.example.bookstore.bdd;

import io.techthinking.flowbdd.report.junit5.results.extension.FlowBDD;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This is very similar to a standard Spring integration test.
 * <p>
 * For comparison please see StatusControllerIT and StatusControllerMvcIT.
 * Notice that we have the addition of global state for the response that was not necessary in StatusControllerIT.
 *
 * <pre>
 * The output of this is:
 * Feature: Get book without builders test
 *  Scenario: Get book (PASSED)
 *    When get status is called
 *    Then status running is return
 * </pre>
 */
@ExtendWith(FlowBDD.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatusTest {

    @Autowired
    private TestRestTemplate template;
    private ResponseEntity<String> response;

    @Test
    public void getBook() {
        whenGetStatusIsCalled();
        thenStatusRunningIsReturned();
    }

    private void whenGetStatusIsCalled() {
        response = template.getForEntity("/status", String.class);
    }

    private void thenStatusRunningIsReturned() {
        assertThat(response.getBody()).isEqualTo("running");
    }
}
