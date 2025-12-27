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

package io.techthinking.flowbdd.examples.devteam.controller;

import io.techthinking.flowbdd.examples.devteam.model.ProductivityBoost;
import io.techthinking.flowbdd.examples.devteam.model.TechDept;
import io.techthinking.flowbdd.examples.devteam.service.DevTeamService;
import org.springframework.web.bind.annotation.*;

@RestController
public class DevController {

    private final DevTeamService devTeamService;

    public DevController(DevTeamService devTeamService) {
        this.devTeamService = devTeamService;
    }

    @PostMapping("/dev/{name}/drinks-coffee")
    public ProductivityBoost drinksCoffee(@PathVariable String name) {
        return devTeamService.dev(name).drinksCoffee();
    }

    @PostMapping("/dev/{name}/does-no-testing")
    public TechDept doesNoTesting(@PathVariable String name) {
        return devTeamService.dev(name).doesNoTesting();
    }
}
