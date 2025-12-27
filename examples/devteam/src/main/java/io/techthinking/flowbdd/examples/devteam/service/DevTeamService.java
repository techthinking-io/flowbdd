/*
 * Flow BDD - The productive way to test.
 * Copyright (C)  2025  James Bayliss
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

package io.techthinking.flowbdd.examples.devteam.service;

import io.techthinking.flowbdd.examples.devteam.model.ProductivityBoost;
import io.techthinking.flowbdd.examples.devteam.model.TechDept;
import org.springframework.stereotype.Service;

@Service
public class DevTeamService {

    public DeveloperFluent dev(String name) {
        return new DeveloperFluent(name);
    }

    public static class DeveloperFluent {
        private final String name;

        public DeveloperFluent(String name) {
            this.name = name == null || name.isBlank() ? "Anonymous Dev" : name;
        }

        /**
         * More messages:
         * name + " reaches maximum caffeine velocity!";
         */
        public ProductivityBoost drinksCoffee() {
            int boost = 40;
            String msg = "1.21 Gigawatts of caffeine! " + name + " is seeing some serious productivity.";
            return new ProductivityBoost(name, boost, msg);
        }

        /**
         * More messages:
         * name + " I find your lack of unit tests... disturbing.";
         * name + " Tonight, we dine in Technical Debt! Prepare for glory (and a 3 a.m. page)!";
         */
        public TechDept doesNoTesting() {
            int debt = 10;
            String msg = name + " tonight, we dine in Technical Debt! For tomorrow, we push to production";
            return new TechDept(name, debt, msg);
        }
    }
}
