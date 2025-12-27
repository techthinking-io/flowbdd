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

package io.techthinking.flowbdd.wordify.tokenize;

import java.util.HashMap;
import java.util.Map;

public class TokenizeParameterMap {
    private final Map<String, TokenizeParameter> map = new HashMap<>();

    public void put(TokenizeParameter parameter) {
        map.put(parameter.getName(), parameter);
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }

    public TokenizeParameter get(String key) {
        return map.get(key);
    }
}
