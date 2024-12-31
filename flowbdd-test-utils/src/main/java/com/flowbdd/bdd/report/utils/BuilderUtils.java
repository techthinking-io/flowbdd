/*
 * Smart BDD - The smart way to do behavior-driven development.
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

package com.flowbdd.bdd.report.utils;

import java.util.List;
import java.util.stream.Collectors;

public class BuilderUtils {

    /**
     * <pre>{@code
     * Builder class
     * public ParentItem build() {
     * return new ParentItem(build(items), childString);
     * }
     * <p>
     * private List<ItemBuilder> testCases; build(items)
     * Pros - only need to implement build()
     * Cons - you might end up with complex build(Map<String, List<Integer>)
     * private ItemListBuilder items; items.build()
     * Pros - can handle any datatype
     * Cons - have to create extra collection builders
     * <p>
     *
     * Collections
     * Also consider
     * public ParentItem withItems(List<ItemBuilder> itemBuilders) {
     * this.itemBuilders.clear();
     * this.itemBuilders.addAll(itemBuilders);
     * return this;
     * }
     * <p>
     * public ParentItem addItem(ItemBuilder itemBuilder) {
     * this.itemBuilders.add(itemBuilder);
     * return this;
     * }
     * }</pre>
     */
    public static <T, B extends Builder<T>> List<T> build(List<B> builders) {
        return builders.stream().map(Builder::build).collect(Collectors.toList());
    }
}
