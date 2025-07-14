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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for the {@link ValidateProcessor} class.
 */
public class ValidateProcessorTest {

    @Test
    public void testValidatePackage() throws IOException, ClassNotFoundException {
        ValidateProcessor processor = new ValidateProcessor();
        List<ValidationResult> results = processor.validatePackage("io.techthinking.flowbdd.validate");

        // There should be at least 3 validation results (from ValidateRunnerTest)
        assertThat(results).hasSizeGreaterThanOrEqualTo(3);

        // All validations should pass
        assertThat(results).allMatch(ValidationResult::isSuccess);
    }

    @Test
    public void testValidatePackageAndThrow() throws IOException, ClassNotFoundException {
        ValidateProcessor processor = new ValidateProcessor();

        // This should not throw an exception
        assertThat(processor.validatePackage("io.techthinking.flowbdd.validate"))
            .isNotEmpty()
            .allMatch(ValidationResult::isSuccess);

        // Create a processor with a scanner that always returns a failing validation
        ValidateScanner scanner = new ValidateScanner() {
            @Override
            public List<ValidationResult> scanPackage(String packageName) {
                List<ValidationResult> results = new ArrayList<>();
                results.add(new ValidationResult(
                    null,
                    new ValidationExpression("(1) = 2"),
                    false,
                    "Test failure"
                ));
                return results;
            }
        };

        ValidateProcessor failingProcessor = new ValidateProcessor(scanner);

        // This should throw a ValidationException
        assertThatThrownBy(() -> failingProcessor.validatePackageAndThrow("io.techthinking.flowbdd.validate"))
            .isInstanceOf(ValidateProcessor.ValidationException.class)
            .hasMessageContaining("Test failure");
    }
}
