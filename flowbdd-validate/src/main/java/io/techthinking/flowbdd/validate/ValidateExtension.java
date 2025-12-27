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

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JUnit 5 extension for validating methods annotated with {@link Validate}.
 * 
 * This extension scans the specified packages for methods annotated with {@link Validate}
 * and validates them before running tests.
 */
public class ValidateExtension implements BeforeAllCallback {
    private final ValidateProcessor processor;
    
    /**
     * Creates a new ValidateExtension with a default processor.
     */
    public ValidateExtension() {
        this.processor = new ValidateProcessor();
    }
    
    /**
     * Creates a new ValidateExtension with the specified processor.
     * 
     * @param processor The processor to use
     */
    public ValidateExtension(ValidateProcessor processor) {
        this.processor = processor;
    }
    
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        Class<?> testClass = context.getRequiredTestClass();
        
        // Check if the test class is annotated with @ValidatePackages
        if (testClass.isAnnotationPresent(ValidatePackages.class)) {
            ValidatePackages annotation = testClass.getAnnotation(ValidatePackages.class);
            String[] packages = annotation.value();
            
            // Validate each package
            for (String packageName : packages) {
                processor.validatePackageAndThrow(packageName);
            }
        }
    }
    
    /**
     * Annotation for specifying packages to validate.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ValidatePackages {
        /**
         * The packages to validate.
         * 
         * @return The packages to validate
         */
        String[] value();
    }
}