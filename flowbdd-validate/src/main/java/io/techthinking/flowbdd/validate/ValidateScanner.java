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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Scanner for finding and validating methods annotated with {@link Validate}.
 * 
 * This class provides functionality to scan packages for methods annotated with {@link Validate},
 * and validate them against their validation expressions.
 */
public class ValidateScanner {
    
    /**
     * Scans the specified package for classes with methods annotated with {@link Validate}
     * and validates them.
     * 
     * @param packageName The package to scan
     * @return A list of validation results
     * @throws IOException If an I/O error occurs
     * @throws ClassNotFoundException If a class cannot be found
     */
    public List<ValidationResult> scanPackage(String packageName) throws IOException, ClassNotFoundException {
        List<ValidationResult> results = new ArrayList<>();
        
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File directory = new File(resource.getFile());
            
            if (directory.exists()) {
                results.addAll(findValidateMethodsInDirectory(directory, packageName));
            }
        }
        
        return results;
    }
    
    /**
     * Recursively finds classes with methods annotated with {@link Validate} in a directory.
     * 
     * @param directory The directory to search
     * @param packageName The package name for classes in this directory
     * @return A list of validation results
     * @throws ClassNotFoundException If a class cannot be found
     */
    private List<ValidationResult> findValidateMethodsInDirectory(File directory, String packageName) 
            throws ClassNotFoundException {
        List<ValidationResult> results = new ArrayList<>();
        
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    results.addAll(findValidateMethodsInDirectory(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                    results.addAll(validateClass(className));
                }
            }
        }
        
        return results;
    }
    
    /**
     * Validates methods annotated with {@link Validate} in the specified class.
     * 
     * @param className The fully qualified class name
     * @return A list of validation results
     * @throws ClassNotFoundException If the class cannot be found
     */
    private List<ValidationResult> validateClass(String className) throws ClassNotFoundException {
        List<ValidationResult> results = new ArrayList<>();
        
        Class<?> clazz = Class.forName(className);
        Object instance;
        
        try {
            instance = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            // Skip classes that can't be instantiated
            return results;
        }
        
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Validate.class)) {
                Validate annotation = method.getAnnotation(Validate.class);
                ValidationExpression expression = new ValidationExpression(annotation.value());
                
                results.add(validateMethod(method, instance, expression));
            }
        }
        
        return results;
    }
    
    /**
     * Validates a method against its validation expression.
     * 
     * @param method The method to validate
     * @param instance The instance on which to invoke the method
     * @param expression The validation expression
     * @return The validation result
     */
    private ValidationResult validateMethod(Method method, Object instance, ValidationExpression expression) {
        try {
            // Convert inputs to appropriate types based on method parameter types
            Class<?>[] parameterTypes = method.getParameterTypes();
            List<Object> inputs = expression.getInputs();
            
            if (parameterTypes.length != inputs.size()) {
                return new ValidationResult(
                    method,
                    expression,
                    false,
                    String.format("Method %s expects %d parameters, but validation expression provides %d",
                            method.getName(), parameterTypes.length, inputs.size())
                );
            }
            
            Object[] args = new Object[inputs.size()];
            for (int i = 0; i < inputs.size(); i++) {
                args[i] = convertToType(inputs.get(i), parameterTypes[i]);
            }
            
            // Invoke the method
            method.setAccessible(true);
            Object result = method.invoke(instance, args);
            
            // Compare the result with the expected output
            Object expectedOutput = expression.getExpectedOutput();
            if (expectedOutput == null && result == null) {
                return new ValidationResult(method, expression, true, "Validation passed");
            } else if (expectedOutput != null && expectedOutput.equals(result)) {
                return new ValidationResult(method, expression, true, "Validation passed");
            } else {
                return new ValidationResult(
                    method,
                    expression,
                    false,
                    String.format("Validation failed: expected %s but got %s", expectedOutput, result)
                );
            }
        } catch (Exception e) {
            return new ValidationResult(
                method,
                expression,
                false,
                String.format("Error during validation: %s", e.getMessage())
            );
        }
    }
    
    /**
     * Converts a value to the specified type.
     * 
     * @param value The value to convert
     * @param type The target type
     * @return The converted value
     */
    private Object convertToType(Object value, Class<?> type) {
        if (value == null) {
            return null;
        }
        
        if (type.isAssignableFrom(value.getClass())) {
            return value;
        }
        
        if (type == int.class || type == Integer.class) {
            if (value instanceof Number) {
                return ((Number) value).intValue();
            } else if (value instanceof String) {
                return Integer.parseInt((String) value);
            }
        } else if (type == double.class || type == Double.class) {
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            } else if (value instanceof String) {
                return Double.parseDouble((String) value);
            }
        } else if (type == String.class) {
            return value.toString();
        }
        
        throw new IllegalArgumentException(String.format("Cannot convert %s to %s", value, type));
    }
}