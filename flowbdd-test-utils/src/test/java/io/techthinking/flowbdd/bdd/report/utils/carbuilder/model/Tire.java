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

package io.techthinking.flowbdd.bdd.report.utils.carbuilder.model;

import java.util.Objects;

public class Tire {
    final int size;
//    final int sectionWidthInMillimeters;
//    final int aspectRation;

    //205/60R15 91V
    //205 = Section Width in Millimeters
    //60 = Aspect Ration
    //    R = Radial Construction
    //15 = Rim diameter in Inches
    //91 = Load Index Service Description
    //    V = Speed Symbol


    public Tire(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tire)) return false;
        Tire wheel = (Tire) o;
        return size == wheel.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size);
    }

    @Override
    public String toString() {
        return "Wheel{" +
            "size=" + size +
            '}';
    }
}
