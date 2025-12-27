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

package io.techthinking.flowbdd.examples.devteam.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


public class TechDept {
    private final String developer;
    private final int debt;
    private final String message;

    @JsonCreator
    public TechDept(
        @JsonProperty("developer") String developer,
        @JsonProperty("debt") int debt,
        @JsonProperty("message") String message) {
        this.developer = developer;
        this.debt = debt;
        this.message = message;
    }

    public String getDeveloper() {
        return developer;
    }

    public int getDebt() {
        return debt;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TechDept)) return false;
        TechDept techDept = (TechDept) o;
        return debt == techDept.debt && Objects.equals(developer, techDept.developer) && Objects.equals(message, techDept.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(developer, debt, message);
    }

    @Override
    public String toString() {
        return "TechDept{" +
            "developer='" + developer + '\'' +
            ", debt=" + debt +
            ", message='" + message + '\'' +
            '}';
    }
}
