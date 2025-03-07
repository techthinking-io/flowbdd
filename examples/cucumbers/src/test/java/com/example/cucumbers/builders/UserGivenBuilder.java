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

package com.example.cucumbers.builders;

import com.example.cucumbers.model.UserGiven;
import io.techthinking.flowbdd.bdd.report.utils.Builder;

public final class UserGivenBuilder implements Builder<UserGiven>  {
    private boolean isHungry;

    private UserGivenBuilder() {
    }

    public static UserGivenBuilder iAm() {
        return new UserGivenBuilder();
    }

    public UserGivenBuilder notHungry() {
        this.isHungry = false;
        return this;
    }

    public UserGivenBuilder withIsHungry(boolean isHungry) {
        this.isHungry = isHungry;
        return this;
    }

    public UserGiven build() {
        return new UserGiven(isHungry);
    }
}
