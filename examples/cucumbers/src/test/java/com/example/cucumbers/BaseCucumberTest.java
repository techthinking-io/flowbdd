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

package com.example.cucumbers;

import com.example.cucumbers.builders.CucumberBuilder;
import com.example.cucumbers.builders.CucumberGivenBuilder;
import com.example.cucumbers.builders.UserGivenBuilder;
import com.example.cucumbers.model.Cucumber;
import com.example.cucumbers.model.CucumberGiven;
import com.example.cucumbers.model.CucumberThen;
import com.example.cucumbers.model.CucumberWhen;
import com.example.cucumbers.model.UserGiven;
import io.techthinking.flowbdd.report.junit5.test.BaseTest;
import io.techthinking.flowbdd.bdd.report.utils.ThenBuilder;
import io.techthinking.flowbdd.bdd.report.utils.WhenBuilder;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.stream.Collectors;

import static io.techthinking.flowbdd.report.mermaid.MessageBuilder.aMessage;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseCucumberTest extends BaseTest {

    private final CucumberService cucumberService = new CucumberService();

    @BeforeEach
    void setUp() {
        sequenceDiagram()
            .addActor("User")
            .addParticipant("CucumberService");
    }

    @Override
    public void doc() {}

    public void given(CucumberBuilder... cucumbers) {
        given(CucumberGivenBuilder.iHave(cucumbers));
    }

    public void given(UserGivenBuilder builder) {
        UserGiven given = builder.build();
        if (!given.isHungry()) {
            notes().text().add("Not hungry, so will not eat");
        }
        sequenceDiagram().add(aMessage().from("User").to("CucumberService").text("setHungry false"));
        cucumberService.setHungry(given.isHungry());
    }

    public void given(CucumberGivenBuilder builder) {
        CucumberGiven cucumberGiven = builder.build();
        cucumberService.setCucumbers(cucumberGiven.getCucumbers());
    }

    public void when(WhenBuilder<CucumberWhen> builder) {
        CucumberWhen cucumberWhen = builder.build();
        cucumberService.eat(cucumberWhen.getColour());
        sequenceDiagram().add(aMessage().from("User").to("CucumberService").text("eat " + cucumberWhen.getColour()));
    }

    public void then(ThenBuilder<CucumberThen> builder) {
        CucumberThen cucumberThen = builder.build();
        final List<Cucumber> actualCucumbers = cucumberService.getCucumbers();
        sequenceDiagram().add(aMessage().from("User").to("CucumberService").text("get cucumbers").type("-->>"));
        sequenceDiagram().add(aMessage().from("CucumberService").to("User").text("cucumbers: " + actualCucumbers).type("-->>"));

        cucumberThen.getColour().ifPresent(colour -> actualCucumbers
            .forEach((cucumber -> assertThat(cucumber.getColour()).isEqualTo(colour))));
        cucumberThen.getQuantity().ifPresent(quantity -> assertThat(actualCucumbers.size()).isEqualTo(quantity));

        cucumberThen.getCucumbers().ifPresent(
            cucumbers -> assertThat(actualCucumbers).containsAll(
                cucumbers.stream().map(CucumberBuilder::build).collect(Collectors.toList())));
    }
}
