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

package io.techthinking.flowbdd.wordify;

import io.techthinking.flowbdd.wordify.legacy.TokenizeClass;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.List;
import java.util.Optional;

public class WordifyExtensionContext {
    static final boolean WORDIFY_NEW = true;

    private WordifyClass wordify = new WordifyClass();
    private TokenizeClass tokenizeClass = new TokenizeClass(new MethodExtractor());

    public Optional<String> wordify(ExtensionContext context, List<Object> parameters) {
        if (WORDIFY_NEW) {
            return context.getTestMethod()
                .map(method -> tokenizeClass.tokenizeAsString(context.getRequiredTestClass(), method.getName(), parameters));
        } else {
            return context.getTestMethod()
                .map(method -> wordify.wordify(context.getRequiredTestClass(), method.getName(), parameters));
        }
    }
}
