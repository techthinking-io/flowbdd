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

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Processor for validating methods annotated with {@link Validate}.
 * 
 * This class provides a convenient way to run validations on methods in a package.
 */
public class ValidateProcessor {
    private final ValidateScanner scanner;
    
    /**
     * Creates a new ValidateProcessor with a default scanner.
     */
    public ValidateProcessor() {
        this.scanner = new ValidateScanner();
    }
    
    /**
     * Creates a new ValidateProcessor with the specified scanner.
     * 
     * @param scanner The scanner to use
     */
    public ValidateProcessor(ValidateScanner scanner) {
        this.scanner = scanner;
    }
    
    /**
     * Validates all methods annotated with {@link Validate} in the specified package.
     * 
     * @param packageName The package to scan
     * @return A list of validation results
     * @throws IOException If an I/O error occurs
     * @throws ClassNotFoundException If a class cannot be found
     */
    public List<ValidationResult> validatePackage(String packageName) throws IOException, ClassNotFoundException {
        return scanner.scanPackage(packageName);
    }
    
    /**
     * Validates all methods annotated with {@link Validate} in the specified package and throws an exception if any validation fails.
     * 
     * @param packageName The package to scan
     * @throws IOException If an I/O error occurs
     * @throws ClassNotFoundException If a class cannot be found
     * @throws ValidationException If any validation fails
     */
    public void validatePackageAndThrow(String packageName) throws IOException, ClassNotFoundException, ValidationException {
        List<ValidationResult> results = validatePackage(packageName);
        List<ValidationResult> failures = results.stream()
                .filter(result -> !result.isSuccess())
                .collect(Collectors.toList());
        
        if (!failures.isEmpty()) {
            throw new ValidationException("Validation failed", failures);
        }
    }
    
    /**
     * Exception thrown when validation fails.
     */
    public static class ValidationException extends Exception {
        private final List<ValidationResult> failures;
        
        /**
         * Creates a new ValidationException.
         * 
         * @param message The exception message
         * @param failures The validation failures
         */
        public ValidationException(String message, List<ValidationResult> failures) {
            super(message + ": " + failures.stream()
                    .map(ValidationResult::toString)
                    .collect(Collectors.joining(", ")));
            this.failures = failures;
        }
        
        /**
         * Gets the validation failures.
         * 
         * @return The validation failures
         */
        public List<ValidationResult> getFailures() {
            return failures;
        }
    }
}