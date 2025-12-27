/*
 * Flow BDD - The most productive way to test.
 * Copyright (C) 2021-2025 James Bayliss.
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

package io.techthinking.flowbdd.examples.cucumbers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.techthinking.flowbdd.examples.cucumbers.builders.CucumberBuilder.aCucumber;
import static io.techthinking.flowbdd.examples.cucumbers.builders.CucumberBuilder.andACucumber;
import static io.techthinking.flowbdd.examples.cucumbers.builders.CucumberGivenBuilder.iHave;
import static io.techthinking.flowbdd.examples.cucumbers.builders.CucumberThenBuilder.iShouldHave;
import static io.techthinking.flowbdd.examples.cucumbers.builders.CucumberThenBuilder.iShouldHaveCucumbers;
import static io.techthinking.flowbdd.examples.cucumbers.builders.CucumberWhenBuilder.iRequestToEatACucumber;
import static io.techthinking.flowbdd.examples.cucumbers.builders.UserGivenBuilder.iAm;

/**
 * Tutorial and example usage of builders
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EatCucumbersTest extends BaseCucumberTest {

    @Override
    public void doc() {
        featureNotes("Working progress for example of usage Flow BDD");
    }

    /**
     * First test shows the basic building blocks of using builders.
     * Builders are not mandatory - if you have a non-trivial app to test, using builders is your best option!
     * <p>
     * - given() accepts a GivenBuilder - setup state for the scenario.
     * - when()  accepts a WhenBuilder  - the request state for the scenario.
     * - then()  accepts a ThenBuilder  - the expected state for the scenario.
     * </p>
     * Output:
     * Given I have a cucumber with colour "red"
     * When I request to eat a cucumbers with colour "red"
     * Then I should have cucumbers with quantity 0
     * <p>
     * If you follow the naming convention you should construct an API that is easy to read, use and maintain.
     * - iHave() is syntax sugar for CucumberGivenBuilder - setup state
     * - iRequestToEatCucumbers() is syntax sugar for CucumberWhenBuilder - request state
     * - iShouldHaveCucumbers() is syntax sugar for CucumberThenBuilder - expected state
     * </p>
     *
     * <p>
     * Starting to test any project and thinking about the nouns, verbs and or the bounded context is hard.
     * The boilerplate code can be a little tricky.
     * </p>
     */
    @Order(0)
    @Test
    void givenOneRedCucumber_whenIEatOneRed_IHaveNoneLeft() {
        given(iHave(aCucumber().withColour("red")));
        when(iRequestToEatACucumber().withColour("red"));
        then(iShouldHaveCucumbers().withQuantity(0));
    }

    /**
     * For this given() you can technically omit iHave() and pass in CucumberBuilders to build the state.
     * This is an anti-pattern - you should use iHave that returns a CucumberGivenBuilder
     * <p>
     * Original Output: Given I have a cucumber with colour "red"
     * Shorter  Output: Given a cucumber with colour "red"
     * <p>
     * This shorter version lacks the context of who has the Cucumbers. But is nicer when writing the test to omit iHave().
     */
    @Order(1)
    @Test
    void givenOneRedCucumber_whenIEatOneRed_IHaveNoneLeft_omittingIHave() {
        given(aCucumber().withColour("red"));
        when(iRequestToEatACucumber().withColour("red"));
        then(iShouldHaveCucumbers().withQuantity(0));
    }

    /**
     * Incremental update. Notice the optional syntax sugar andACucumber()
     * <p>
     * Output:
     * Given I have a cucumber with colour "red" and a cucumber with colour "blue"
     * When I request to eat cucumbers with quantity 1 with colour "red"
     * Then I should have cucumbers with quantity 1
     */
    @Order(2)
    @Test
    void givenOneRedAndOneBlueCucumber_whenIEatOneRed_IhaveOneCucumberLeft() {
        given(iHave(aCucumber().withColour("red"), andACucumber().withColour("blue")));
        when(iRequestToEatACucumber().withColour("red"));
        then(iShouldHaveCucumbers().withQuantity(1));
    }

    /**
     * Let's update the expected state to assert on colour.
     */
    @Order(3)
    @Test
    void givenOneRedAndOneBlueCucumber_whenIEatOneRed_IhaveOneBlueCucumberLeft() {
        given(iHave(aCucumber().withColour("red"), andACucumber().withColour("blue")));
        when(iRequestToEatACucumber().withColour("red"));
        then(iShouldHaveCucumbers().withQuantity(1).withColour("blue"));
    }

    /**
     * Let's update the expected state to do an exact match
     */
    @Order(4)
    @Test
    void givenOneRedAndOneBlueCucumber_whenIEatOneRed_IhaveOneBlueCucumberLeft_betterAssertion() {
        given(iHave(aCucumber().withColour("red"), andACucumber().withColour("blue")));
        when(iRequestToEatACucumber().withColour("red"));
        then(iShouldHave(aCucumber().withColour("blue")));
    }

    /**
     * Adding a new feature of not hungry
     * <p>
     * Create new given(UserGivenBuilder builder)
     * <pre>
     * With this new feature we are adding some notes. This shows how we can add to the report.
     * if (!given.isHungry()) {
     *   notes().text().add("Not hungry, so will not eat");
     * }
     * </pre>
     */
    @Order(5)
    @Test
    void givenOneRedAndOneBlueCucumberAndNotHungry_whenIRequestToEatOneRed_IhaveOneRedAndOneBlueCucumberLeft() {
        given(iAm().notHungry());
        given(iHave(aCucumber().withColour("red"), andACucumber().withColour("blue")));
        when(iRequestToEatACucumber().withColour("red"));
        then(iShouldHaveCucumbers().withQuantity(2));
    }

    /**
     * <pre>
     * Next tests:
     * - show my_test(@Given int amount, @Given String colour).
     * </pre>
     */
    @Test
    void todo() {
        //TODO
    }

    /**
     * Maybe tackle quantities
     * Factory methods below can be considered ant-patterns, if you add more fields you have maintenance issues and hard to use defaults. In the long run they will be limiting.
     * - iHave(5, "red")
     * - iHave(5, "red").cucumbers()
     * - iEatCucumbers(5, "red")
     * - iEatCucumbers(5, redCucumber())
     * - iEatCucumbers(5, cucumbers("red"))
     * Above it definitely reads better.
     *  * Hopefully generating builders will resolve this issue in the future.
     *  * iRequestToEatCucumbers().withQuantity(1).withColour("red") could generate "I request to eat 1 red cucumber"
     **/
    @Test
    void todo2() {
        //TODO
    }
}
