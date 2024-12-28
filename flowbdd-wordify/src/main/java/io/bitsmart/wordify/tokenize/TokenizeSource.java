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

package io.bitsmart.wordify.tokenize;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.bitsmart.wordify.tokenize.TokenType.CHAR;
import static io.bitsmart.wordify.tokenize.TokenType.NUMBER;
import static io.bitsmart.wordify.tokenize.TokenType.STRING_LITERAL;

public class TokenizeSource {
    public static final char NEW_LINE = '\n';
    private static final char SPACE = ' ';
    private static final char TAB = '\t';
    private static final char COMMA = ',';
    private static final char SEMI_COLON = ';';
    private static final char HYPHEN = '-';
    private static final char FULL_STOP = '.';
    private static final char LEFT_PARENTHESIS = '(';
    private static final char RIGHT_PARENTHESIS = ')';
    private static final char DOUBLE_QUOTE = '"';
    private static final char SINGLE_QUOTE = '\'';
    private static final char BACK_SLASH = '\\';
    private static final char END = 0x003;

    private int index = 0;
    private final char[] input;
    private final String original;
    private final TokenizeParameterMap parameterMap;
    private int startingWhiteSpace;
    private boolean newLineOfCode = true;


    public TokenizeSource(String original, TokenizeParameterMap parameterMap) {
        this.input = original.toCharArray();
        this.original = original;
        this.parameterMap = parameterMap;
    }

    public JavaSourceTokens tokenize() {
        List<Token> tokens = new ArrayList<>();
        startingWhiteSpace = incrementIndexPastWhiteSpace(0);
        __println("startingWhiteSpace: " + startingWhiteSpace);
        handleNextToken().ifPresent(tokens::add);
        char ch;
        int beforeIndex = -1;
        while (inBounds()) {
            ch = get();
            __println("MAIN_LOOP_PROCESS_NEXT: '" + ch + "'");
            if (beforeIndex == index()) {
                incrementIndex();
                __println("MAIN_LOOP_INDEX_NOT_INCREMENTED: '" + ch + "'" + ", index: " + index());
            }
            beforeIndex = index();
            incrementIndexPastCharsBetweenFieldOrMethodNames();
            if (isEnd(ch)) {
                break;
            }
            Optional<Token> token = handleNextToken();
            token.ifPresent(tokens::add);
            __println("TOKEN_TO_ADD: " + token);
        }
        return new JavaSourceTokens(tokens, startingWhiteSpace);
    }

    /**
     * newline, FieldOrMethodName, string literal
     */
    private Optional<Token> handleNextToken() {
        char ch = get();
        __print("NEXT_FIELD_OR_METHOD_NAME <start>");

        __print("'" + ch + "', ");
        if (ch == NEW_LINE) {
            return handleNewLine();
        } else if (Character.isJavaIdentifierStart(ch)) {
            return handleFieldOrMethodNameChar();
        } else if (Character.isDigit(ch)) {
            return handleNumber();
        } else if (ch == DOUBLE_QUOTE) {
            return handleStringLiteral();
        } else if (ch == SINGLE_QUOTE) {
            return handleChar();
        } else {
            return Optional.empty();
        }
    }

    private boolean isSingleQuoteChar(Character ch) {
        return ch == SINGLE_QUOTE;
    }

    private boolean isFieldOrMethodNameChar(Character ch) {
        return Character.isJavaIdentifierStart(ch) || Character.isDigit(ch);
    }

    private boolean isDecimalNumberChar(Character ch) {
        return Character.isDigit(ch) || ch == '.';
    }

    private boolean isOctalNumberChar(Character ch) {
        return Character.isDigit(ch) || ch == '.' || ch == 'x' || ch >= 'a' && ch <= 'f' || ch >= 'A' && ch <= 'F';
    }

    private Optional<Token> handleFieldOrMethodNameChar() {
        int beginIndex = index();
        int count = 0;
        char ch = get();
        __print("NEXT_FIELD_OR_METHOD_NAME <start>");

        while (inBounds()) {
            __print("'" + ch + "', ");
            if (ch == NEW_LINE) {
                break;
            } else if (isFieldOrMethodNameChar(ch)) {
                ch = incrementIndexAndGet();
                count++;
            } else {
                break;
            }
        }
        __println("<end> beginIndex: " + beginIndex + ", count: " + count);
        if (count == 0) {
            return Optional.empty();
        }

        String str = substring(beginIndex, beginIndex + count);
        if (parameterMap.contains(str)) {
            Object value = parameterMap.get(str).getValue();
            str = value == null ? "null" : String.valueOf(value);
            //str = WordifyStringUtil.wordifyMethodOrFieldName(str);
        } else {
            str = WordifyStringUtil.wordifyMethodOrFieldName(input, beginIndex, beginIndex + count);
        }
        if (newLineOfCode) {
            str = WordifyStringUtil.upperCaseFirstChar(str);
        }
        newLineOfCode = false;
        return Optional.of(new Token(str, TokenType.DEFAULT));
    }

    private Optional<Token>  handleNumber() {
        int beginIndex = index();
        int count = 0;
        char ch = get();
        __print("NEXT_NUMBER <start>");

        boolean octal = false;
        if (ch == '0' && peekNext() == 'x') {
            octal = true;
        }

        while (inBounds()) {
            __print("'" + ch + "', ");
            if (ch == NEW_LINE) {
                break;
            } else if ((octal && isOctalNumberChar(ch)) || (!octal && isDecimalNumberChar(ch))) {
                ch = incrementIndexAndGet();
                count++;
            } else if (!octal && (ch == 'f' || ch == 'L')) {
                // float and long suffix
                incrementIndex();
            } else {
                break;
            }
        }
        __println("<end> beginIndex: " + beginIndex + ", count: " + count + ", octal: " + octal);
        if (count == 0) {
            return Optional.empty();
        }

        // consume if ends in .
        if (getPrevious() == '.') {
            count--;
        }

        return Optional.of(new Token(substring(beginIndex, beginIndex + count), NUMBER));
    }

    private Optional<Token> handleNewLine() {
        int beginIndex = index();
        incrementIndex();

        int endIndex = beginIndex + 1;
        int whiteSpace = incrementIndexPastWhiteSpace(0);
        int adjusted = 0;

        if (whiteSpace > startingWhiteSpace) {
            adjusted = whiteSpace - startingWhiteSpace;
            endIndex = endIndex + adjusted;
        }
        if (adjusted == 0) {
            newLineOfCode = true;
        }
        __println("<end> " + " handling new line whiteSpace: " + whiteSpace + ", adjusted whiteSpace: " + adjusted + "index: " + index + ", input.length: " + input.length + ", beginIndex: " + beginIndex + ", endIndex: " + endIndex);

        if (beginIndex == endIndex) {
            return Optional.empty();
        }
        return Optional.of(new Token(substring(beginIndex, endIndex), TokenType.NEW_LINE));
    }

    private Optional<Token> handleStringLiteral() {
        int beginIndex = index();
        __print("NEXT_STRING_LITERAL <start>");
        __print("'" + get() + "', ");
        incrementIndex();

        int count = 1;
        char ch = get();

        while (inBounds()) {
            __print("'" + ch + "', ");
            // escaped double quote / and peek ". Increment index past the double quote.
            if (ch == BACK_SLASH && peekNext() == DOUBLE_QUOTE) {
                incrementIndexAndGet();
                ch = incrementIndexAndGet();
                count += 2;
            } else if (ch == NEW_LINE) {
                break;
            } else if (ch != DOUBLE_QUOTE) {
                ch = incrementIndexAndGet();
                count++;
            } else {
                incrementIndex();
                count++;
                break;
            }
        }
        __println("<end> beginIndex: " + beginIndex + ", count: " + count);
        if (count == 0) {
            return Optional.empty();
        }
        return Optional.of(new Token(substring(beginIndex, beginIndex + count), STRING_LITERAL));
    }

    /** you can only have 3 chars i.e. 'a'. But safer to loop. */
    private Optional<Token> handleChar() {
        int beginIndex = index();
        __print("NEXT_CHAR <start>");
        __print("'" + get() + "', ");
        incrementIndex();

        int count = 1;
        char ch = get();

        while (inBounds()) {
            __print("'" + ch + "', ");
            if (ch == NEW_LINE) {
                break;
            } else if (ch != SINGLE_QUOTE) {
                ch = incrementIndexAndGet();
                count++;
            } else {
                incrementIndex();
                count++;
                break;
            }
        }
        __println("<end> beginIndex: " + beginIndex + ", count: " + count);
        if (count == 0) {
            return Optional.empty();
        }
        return Optional.of(new Token(substring(beginIndex, beginIndex + count), CHAR));
    }

    private int index() {
        return index;
    }

    private void incrementIndex() {
        index++;
    }

    private char incrementIndexAndGet() {
        index++;
        return get();
    }

    private String substring(int beginIndex, int endIndex) {
        return original.substring(beginIndex, endIndex);
    }

    private boolean inBounds() {
        return index < input.length;
    }

    private boolean inBounds(int index) {
        return index < input.length;
    }

    private char get() {
        if (inBounds()) {
            return input[index];
        } else {
            return END;
        }
    }

    private char getPrevious() {
        return input[index - 1];
    }

    private char peekNext() {
        int next = index + 1;
        if (inBounds(next)) {
            return input[next];
        } else {
            return END;
        }
    }

    private boolean isEnd(char ch) {
        return (ch == END);
    }

    private void incrementIndexPastCharsBetweenFieldOrMethodNames() {
        final char ch = get();
        if (isEnd(ch) || ch == NEW_LINE) {
            __println("DO_NOT_CONSUME: '" + ch + "', ");
        } else if (!Character.isJavaIdentifierStart(ch) && !Character.isDigit(ch) && ch != DOUBLE_QUOTE && ch != SINGLE_QUOTE) {
            __println("CONSUME: '" + ch + "', ");
            incrementIndex();
            incrementIndexPastCharsBetweenFieldOrMethodNames();
        }
    }

    private int incrementIndexPastWhiteSpace(int whiteSpace) {
        final char ch = get();
        boolean found = false;
        if (ch == SPACE) {
            whiteSpace++;
            found = true;
        } else if (ch == TAB) {
            whiteSpace += 4;
            found = true;
        }

        if (found) {
            incrementIndex();
            return incrementIndexPastWhiteSpace(whiteSpace);
        }
        return whiteSpace;
    }

    private static final boolean PRINT = false;

    private void __print(Object obj) {
        if (PRINT) {
            System.out.print(obj);
        }
    }

    private void __println(Object obj) {
        if (PRINT) {
            System.out.println(obj);
        }
    }
}