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

package io.techthinking.flowbdd.examples.cucumbers;

import io.techthinking.flowbdd.examples.cucumbers.model.Cucumber;
import io.techthinking.flowbdd.examples.cucumbers.model.User;

import java.util.ArrayList;
import java.util.List;

public class CucumberService {
    private final List<Cucumber> cucumbers = new ArrayList<>();
    private final User user = new User(true);

    public void setHungry(boolean isHungry) {
       user.setHungry(isHungry);
    }

    public void setCucumbers(List<Cucumber> cucumbers) {
        this.cucumbers.clear();
        this.cucumbers.addAll(cucumbers);
    }

    public List<Cucumber> getCucumbers() {
        return cucumbers;
    }

    public void eat(String colour) {
        if (user.isHungry()) {
            for (int i = 0; i < cucumbers.size(); i++) {
                if (cucumbers.get(i).getColour().equals(colour)) {
                    cucumbers.remove(i);
                    break;
                }
            }
        }
    }

    public void eat(int quantity, String colour) {
        if (user.isHungry()) {
            // cucumbers don't have identity - temp code
            int removedCount = 0;
            for (int i = 0; i < cucumbers.size(); i++) {
                if (cucumbers.get(i).getColour().equals(colour)) {
                    cucumbers.remove(i);
                    removedCount++;
                    if (removedCount == quantity) {
                        break;
                    }
                }
            }
        }
    }
//
//    /**  this will not work as cucumbers don't have identity */
//    public void eat(int quantity, String colour) {
//        if (user.isHungry()) {
//
//            List<Cucumber> matchedCucumbers = cucumbers.stream()
//                .filter(cucumber -> colour.equals(cucumber.getColour()))
//                .limit(quantity)
//                .collect(Collectors.toList());
//            cucumbers.removeAll(matchedCucumbers);
//        }
//    }
//
//    public void eatAllThatMatchColour(String colour) {
//        List<Cucumber> matchedCucumbers = cucumbers.stream()
//            .filter(cucumber -> colour.equals(cucumber.getColour()))
//            .collect(Collectors.toList());
//
//        cucumbers.removeAll(matchedCucumbers);
//    }
//
//    public void eatAllThatAreBiggerThan(int size) {
//        List<Cucumber> matchedCucumbers = cucumbers.stream()
//            .filter(cucumber -> size > cucumber.getSize())
//            .collect(Collectors.toList());
//
//        cucumbers.removeAll(matchedCucumbers);
//    }
}
