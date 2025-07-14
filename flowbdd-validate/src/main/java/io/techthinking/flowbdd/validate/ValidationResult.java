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

import java.lang.reflect.Method;

/**
 * Represents the result of validating a method against its validation expression.
 */
public class ValidationResult {
    private final Method method;
    private final ValidationExpression expression;
    private final boolean success;
    private final String message;

    /**
     * Creates a new ValidationResult.
     * 
     * @param method The method that was validated
     * @param expression The validation expression
     * @param success Whether the validation was successful
     * @param message A message describing the validation result
     */
    public ValidationResult(Method method, ValidationExpression expression, boolean success, String message) {
        this.method = method;
        this.expression = expression;
        this.success = success;
        this.message = message;
    }

    /**
     * Gets the method that was validated.
     * 
     * @return The method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Gets the validation expression.
     * 
     * @return The validation expression
     */
    public ValidationExpression getExpression() {
        return expression;
    }

    /**
     * Gets whether the validation was successful.
     * 
     * @return true if the validation was successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Gets a message describing the validation result.
     * 
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns a string representation of the validation result.
     * 
     * @return A string representation of the validation result
     */
    @Override
    public String toString() {
        String methodName = method == null ? "unknown" : 
                method.getDeclaringClass().getSimpleName() + "." + method.getName();
        return String.format("%s: %s - %s",
                methodName,
                success ? "PASSED" : "FAILED",
                message);
    }
}
