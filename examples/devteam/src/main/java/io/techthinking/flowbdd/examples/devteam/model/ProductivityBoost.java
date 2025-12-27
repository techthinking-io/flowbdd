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

package io.techthinking.flowbdd.examples.devteam.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class ProductivityBoost {
    private final String developer;
    private final int boost;
    private final String message;

    @JsonCreator
    public ProductivityBoost(
        @JsonProperty("developer") String developer,
        @JsonProperty("boost") int boost,
        @JsonProperty("message") String message
    ) {
        this.developer = developer;
        this.boost = boost;
        this.message = message;
    }

    public String getDeveloper() {
        return developer;
    }

    public int getBoost() {
        return boost;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductivityBoost)) return false;
        ProductivityBoost that = (ProductivityBoost) o;
        return boost == that.boost && Objects.equals(developer, that.developer) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(developer, boost, message);
    }

    @Override
    public String toString() {
        return "ProductivityBoost{" +
            "developer='" + developer + '\'' +
            ", boost=" + boost +
            ", message='" + message + '\'' +
            '}';
    }
}
