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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses and represents a validation expression.
 * 
 * A validation expression is in the format "(input) = output", where:
 * - input: The input value(s) to the method
 * - output: The expected output value
 */
public class ValidationExpression {
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("\\(([^)]*)\\)\\s*=\\s*(.+)");
    
    private final String rawExpression;
    private final List<Object> inputs;
    private final Object expectedOutput;
    
    /**
     * Creates a new ValidationExpression by parsing the given expression string.
     * 
     * @param expression The validation expression string
     * @throws IllegalArgumentException if the expression is invalid
     */
    public ValidationExpression(String expression) {
        this.rawExpression = expression;
        
        Matcher matcher = EXPRESSION_PATTERN.matcher(expression);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid validation expression: " + expression);
        }
        
        String inputsStr = matcher.group(1).trim();
        String outputStr = matcher.group(2).trim();
        
        this.inputs = parseInputs(inputsStr);
        this.expectedOutput = parseValue(outputStr);
    }
    
    /**
     * Gets the raw expression string.
     * 
     * @return The raw expression string
     */
    public String getRawExpression() {
        return rawExpression;
    }
    
    /**
     * Gets the parsed input values.
     * 
     * @return The input values
     */
    public List<Object> getInputs() {
        return inputs;
    }
    
    /**
     * Gets the parsed expected output value.
     * 
     * @return The expected output value
     */
    public Object getExpectedOutput() {
        return expectedOutput;
    }
    
    /**
     * Parses a comma-separated list of input values.
     * 
     * @param inputsStr The input values string
     * @return The parsed input values
     */
    private List<Object> parseInputs(String inputsStr) {
        List<Object> result = new ArrayList<>();
        
        if (inputsStr.isEmpty()) {
            return result;
        }
        
        String[] parts = inputsStr.split(",");
        for (String part : parts) {
            result.add(parseValue(part.trim()));
        }
        
        return result;
    }
    
    /**
     * Parses a single value from a string.
     * 
     * @param valueStr The value string
     * @return The parsed value
     */
    private Object parseValue(String valueStr) {
        // Try to parse as integer
        try {
            return Integer.parseInt(valueStr);
        } catch (NumberFormatException e) {
            // Not an integer
        }
        
        // Try to parse as double
        try {
            return Double.parseDouble(valueStr);
        } catch (NumberFormatException e) {
            // Not a double
        }
        
        // Check if it's a quoted string
        if (valueStr.startsWith("\"") && valueStr.endsWith("\"")) {
            return valueStr.substring(1, valueStr.length() - 1);
        }
        
        // Default to treating it as a string
        return valueStr;
    }
}