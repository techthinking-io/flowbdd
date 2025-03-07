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

package io.techthinking.flowbdd.report.junit5.results.model.notes;
import io.techthinking.flowbdd.report.mermaid.SequenceDiagram;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public class SequenceDiagramFactory {
    HashMap<String, SequenceDiagram> map = new LinkedHashMap<>();

    //TODO this concept works, needs testing so that we can add "diagram-1"
    public SequenceDiagram add(SequenceDiagram diagram) {
        map.put("diagram-1", diagram);
        return diagram;
    }

    public SequenceDiagram add(String name, SequenceDiagram diagram) {
        map.put(name, diagram);
        return diagram;
    }

    public SequenceDiagram get(String name) {
        return  map.get(name);
    }

    public HashMap<String, SequenceDiagram> getAll() {
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SequenceDiagramFactory)) return false;
        SequenceDiagramFactory that = (SequenceDiagramFactory) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }

    @Override
    public String toString() {
        return "SequenceDiagramFactory{" +
            "map=" + map +
            '}';
    }
}
