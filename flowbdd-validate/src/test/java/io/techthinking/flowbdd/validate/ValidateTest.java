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

package io.techthinking.flowbdd.validate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the validation functionality.
 *
 * This class demonstrates how to use ValidateRunner in different scenarios.
 */
@ExtendWith(ValidateExtension.class)
@ValidateExtension.ValidatePackages("io.techthinking.flowbdd.validate")
public class ValidateTest {

    /**
     * Method that adds 5 to the input value.
     * 
     * @param value The input value
     * @return The input value plus 5
     */
    @Validate("(5) = 10")
    public int plusFive(int value) {
        return value + 5;
    }

    /**
     * Method that concatenates two strings.
     * 
     * @param a The first string
     * @param b The second string
     * @return The concatenated string
     */
    @Validate("(\"Hello\", \"World\") = \"HelloWorld\"")
    public String concatenate(String a, String b) {
        return a + b;
    }

    /**
     * Method that multiplies two numbers.
     * 
     * @param a The first number
     * @param b The second number
     * @return The product of the two numbers
     */
    @Validate("(3, 4) = 12")
    public int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Test for the {@link ValidationExpression} class.
     */
    @Test
    public void testValidationExpression() {
        ValidationExpression expression = new ValidationExpression("(5) = 10");
        assertThat(expression.getInputs()).containsExactly(5);
        assertThat(expression.getExpectedOutput()).isEqualTo(10);

        expression = new ValidationExpression("(\"Hello\", \"World\") = \"HelloWorld\"");
        assertThat(expression.getInputs()).containsExactly("Hello", "World");
        assertThat(expression.getExpectedOutput()).isEqualTo("HelloWorld");

        expression = new ValidationExpression("(3, 4) = 12");
        assertThat(expression.getInputs()).containsExactly(3, 4);
        assertThat(expression.getExpectedOutput()).isEqualTo(12);
    }

    @Test
    public void testCustomValidationProcessor() throws Exception {
        ValidateProcessor processor = new ValidateProcessor();
        List<ValidationResult> results = processor.validatePackage("io.techthinking.flowbdd.validate");

        List<ValidationResult> thisClassResults = new ArrayList<>();
        for (ValidationResult result : results) {
            if (result.getMethod().getDeclaringClass() == getClass()) {
                thisClassResults.add(result);
            }
        }

        for (ValidationResult result : thisClassResults) {
            assertThat(result.isSuccess())
                .as("Validation for " + result.getMethod().getName() + " should pass: " + result.getMessage())
                .isTrue();
        }

        assertThat(thisClassResults.size()).isGreaterThanOrEqualTo(3);
    }
}
