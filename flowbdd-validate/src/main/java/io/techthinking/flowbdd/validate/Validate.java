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

package io.techthinking.flowbdd.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for method validation.
 * 
 * The validation expression is a string that defines expected input-output relationships.
 * For example: "(5) = 10" means that when the method is called with the argument 5, it should return 10.
 * 
 * This annotation can be used with the ValidateRunner to automatically test methods against their validation expressions.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Validate {
    /**
     * The validation expression.
     * 
     * Format: "(input) = output"
     * Where:
     * - input: The input value(s) to the method
     * - output: The expected output value
     * 
     * Examples:
     * - "(5) = 10" - When called with 5, the method should return 10
     * - "(\"hello\") = \"HELLO\"" - When called with "hello", the method should return "HELLO"
     * - "(2, 3) = 5" - When called with 2 and 3, the method should return 5
     * 
     * @return The validation expression
     */
    String value();
}
