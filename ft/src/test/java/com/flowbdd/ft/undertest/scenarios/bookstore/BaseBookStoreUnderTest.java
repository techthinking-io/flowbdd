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

package com.flowbdd.ft.undertest.scenarios.bookstore;

import io.bitsmart.bdd.report.junit5.test.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.condition.EnabledIf;

import static io.bitsmart.bdd.report.mermaid.MessageBuilder.aMessage;

@EnabledIf("isEnabled")
public class BaseBookStoreUnderTest extends BaseTest {
    private static Boolean enabled = false;

    @BeforeEach
    void setUp() {
        sequenceDiagram()
            .addActor("User")
            .addParticipant("BookStore")
            .addParticipant("ISBNdb");
    }

    @Override
    public void doc() {
    }

    public void given(String str) {
    }

    public void when(String str) {
        sequenceDiagram().add(aMessage().from("BookStore").to("ISBNdb").text("/book/isbn"));
        sequenceDiagram().add(aMessage().from("ISBNdb").to("BookStore").text("book"));
    }

    public void then(String str) {
    }

    public String aBookExists() {
        return "aUserRequestsABook";
    }

    public String aUserRequestsABook() {
        return "aUserRequestsABook";
    }

    public String theResponseContainsADefaultIsbnBook() {
        return "theResponseContainsADefaultIsbnBook";
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(Boolean isEnabled) {
        enabled = isEnabled;
    }
}
