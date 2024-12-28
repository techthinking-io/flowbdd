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

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.bitsmart.wordify.tokenize.TokenType.CHAR;
import static io.bitsmart.wordify.tokenize.TokenType.NEW_LINE;
import static io.bitsmart.wordify.tokenize.TokenType.NUMBER;
import static io.bitsmart.wordify.tokenize.TokenType.STRING_LITERAL;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * End goal:
 * "wordify": "Passing assertion with string value 'blah' with int value 1",
 * "wordifyTemplate": "Passing assertion with string value '{{ 0 }}' with int value {{ 1 }} ",
 * "args": [
 * {
 * "type": "String",
 * "value": "blah"
 * },
 * {
 * "type": "Integer",
 * "value": 1
 * }
 * ],
 */
class TokenizeJavaSourceTokensTest {

    @Test
    void bookstoreAsString() {
        String input = "" +
            "        given(theIsbnDbContains().anEntry(\n" +
            "            forAnIsbn(\"default-isbn\")\n" +
            "                .thatWillReturn(anIsbnBook()\n" +
            "                    .withIsbn(\"default-isbn\")\n" +
            "                    .withTitle(\"default-title\")\n" +
            "                    .withAuthor(\"default-author\"))));\n" +
            "        when(aUserRequestsABook().withIsbn(\"default-isbn\"));\n" +
            "        then(theResponseContains(anIsbnBook()\n" +
            "            .withIsbn(\"default-isbn\")\n" +
            "            .withTitle(\"default-title\")\n" +
            "            .withAuthors(singletonList(\"default-author\"))));";

        JavaSourceTokens javaSourceTokens = tokenizeSource(input);

        assertThat(javaSourceTokens.getWhiteSpace()).isEqualTo(8);
        assertThat(javaSourceTokens.asString()).isEqualTo("" +
            "Given the isbn db contains an entry\n" +
            "    for an isbn \"default-isbn\"\n" +
            "        that will return an isbn book\n" +
            "            with isbn \"default-isbn\"\n" +
            "            with title \"default-title\"\n" +
            "            with author \"default-author\"\n" +
            "When a user requests a book with isbn \"default-isbn\"\n" +
            "Then the response contains an isbn book\n" +
            "    with isbn \"default-isbn\"\n" +
            "    with title \"default-title\"\n" +
            "    with authors singleton list \"default-author\"");
    }

    @Test
    void bookstoreFirstLineAsString() {
        String input = "" +
            "        given(theIsbnDbContains().anEntry(\n";

        JavaSourceTokens javaSourceTokens = tokenizeSource(input);

        assertThat(javaSourceTokens.getWhiteSpace()).isEqualTo(8);
        assertThat(javaSourceTokens.asString()).isEqualTo("" +
            "Given the isbn db contains an entry\n");
    }

    @Test
    void oneNewLineAsString() {
        String input = "" +
            "    given\n" +
            "        something";

        JavaSourceTokens javaSourceTokens = tokenizeSource(input);

        assertThat(javaSourceTokens.getWhiteSpace()).isEqualTo(4);
        assertThat(javaSourceTokens.asString()).isEqualTo("" +
            "Given\n" +
            "    something");
    }

    @Test
    void givenWhenThenAsString() {
        String input = "" +
            "    given()\n" +
            "    when()\n" +
            "    then()";

        JavaSourceTokens javaSourceTokens = tokenizeSource(input);

        assertThat(javaSourceTokens.getWhiteSpace()).isEqualTo(4);
        assertThat(javaSourceTokens.asString()).isEqualTo("" +
            "Given\n" +
            "When\n" +
            "Then");
    }


    /**
     * Given, when, then token type or meta token type?. At the start of a new line.
     * Technically only given, when, then should be uppercase
     *
     * Well have a name resolver
     *  given = t("Given", TokenType.GIVEN)
     *  param1 = = t("param1 value", TokenType.ARG, metadata etc...) metadata = object type, maybe allowed values
     */
    @Disabled
    @Test
    void handlesGivenWhenThen() {
        assertTokenize("given(doSomething());", t("Given", TokenType.GIVEN), t("doSomething"));
        assertTokenize("when(doSomething());", t("Given", TokenType.WHEN), t("doSomething"));
        assertTokenize("then(doSomething());", t("Given", TokenType.THEN), t("doSomething"));
    }

    @Test
    void handlesEmptyLine() {
        assertEmptyTokenListStartingAt("", 0);
    }

    @Test
    void handlesWhiteSpaceAtStartOfLine() {
        assertEmptyTokenListStartingAt(" ", 1);
        assertEmptyTokenListStartingAt("  ", 2);
        assertEmptyTokenListStartingAt("\t", 4);
        assertEmptyTokenListStartingAt("\t ", 5);
        assertEmptyTokenListStartingAt(" \t", 5);
        assertEmptyTokenListStartingAt(" \t ", 6);
        assertTokenizeStartingAt(" a", 1, t("A"));
    }

    @Test
    void handlesFieldAndMethodsNames() {
        assertTokenize("doSomething();", t("Do something"));
        assertTokenize("field.doSomething();", t("Field"), t("do something"));
        assertTokenize("field. doSomething();", t("Field"), t("do something"));
        assertTokenize("field .doSomething();", t("Field"), t("do something"));
        assertTokenize("field . doSomething();", t("Field"), t("do something"));
    }

    @Test
    void handlesFieldAndMethodsNames_multiLine() {
        assertTokenize("field\n.doSomething();", t("Field"), t("\n", NEW_LINE), t("Do something"));
        assertTokenize("field. \ndoSomething();", t("Field"), t("\n", NEW_LINE), t("Do something"));
        assertTokenize("field\n .doSomething();", t("Field"), t("\n ", NEW_LINE), t("do something"));
        assertTokenize("field\n  .doSomething();", t("Field"), t("\n  ", NEW_LINE), t("do something"));
    }

    @Test
    void handlesFieldAndMethodsNamesWithNumbers() {
        assertTokenize("doSomething99();", t("Do something 99"));
        assertTokenize("field99.doSomething99();", t("Field 99"), t("do something 99"));
    }

    /**
     * TODO work out the requirements
     * 'a,b'      = A b
     * 'a, b'     = A b
     * 'a,  b'    = A b
     * 'a(b,c)'   = A b c
     * 'a(b, c)'  = A b c
     * 'a (b, c)' = A b c
     * 'a (b, c)' = A b c
     * 'a(b.c,d)' = A b c d
     * 'aB'       = A b
     * 'aBCD'     = A BCD
     * 'aBCDEe'   = A BCD ee
     */
    @Test
    void handlesFieldAndMethodsNamesCommas() {
        assertTokenize("a,b", t("A"), t("b"));
        assertTokenize("a, b", t("A"), t("b"));
        assertTokenize("a,  b", t("A"), t("b"));
        assertTokenize("a ,b", t("A"), t("b"));
        assertTokenize("a , b", t("A"), t("b"));
    }

    @Test
    void handlesBrackets() {
        assertTokenize("a(b,c)", t("A"), t("b"), t("c"));
        assertTokenize("a(b,c.d())", t("A"), t("b"), t("c"), t("d"));
        assertTokenize("a(b,c)", t("A"), t("b"), t("c"));
        assertTokenize("a(b, c)", t("A"), t("b"), t("c"));
        assertTokenize("a( b, c)", t("A"), t("b"), t("c"));
        assertTokenize("a ( b, c )", t("A"), t("b"), t("c"));
        assertTokenize("a  (  b  ,  c  )", t("A"), t("b"), t("c"));
    }

    @Test
    void handlesQuotes() {
        assertTokenize("\"a\"", t("\"a\"", STRING_LITERAL));
        assertTokenize("(\"a\",\"b\")", t("\"a\"", STRING_LITERAL), t("\"b\"", STRING_LITERAL));
    }

    @Test
    void handlesEscapedQuotes() {
        assertTokenize("\"\\\"a\\\"\"", t("\"\\\"a\\\"\"", STRING_LITERAL));
    }

    @Test
    void handlesChars() {
        assertTokenize("char i = 'a'", t("Char"), t("i"), t("'a'", CHAR));
        assertTokenize("('a','b')", t("'a'", CHAR), t("'b'", CHAR));
    }

    @Test
    void handlesNumbers() {
        assertTokenize("99.99", t("99.99", NUMBER));
        assertTokenize("doSomething(99.99);", t("Do something"), t("99.99", NUMBER));
        assertTokenize("long i = 0;", t("Long"), t("i"), t("0", NUMBER));
        assertTokenize("long i = 0L;", t("Long"), t("i"), t("0", NUMBER));
        assertTokenize("float i = 0f;", t("Float"), t("i"), t("0", NUMBER));
        assertTokenize("float i = 0.0f;", t("Float"), t("i"), t("0.0", NUMBER));
        assertTokenize("char i = 0x0;", t("Char"), t("i"), t("0x0", NUMBER));
        assertTokenize("char i = 0xF;", t("Char"), t("i"), t("0xF", NUMBER));
    }

    @Test
    void handlesParameters() {
        TokenizeParameterMap parameterMap = new TokenizeParameterMap();
        parameterMap.put(new TokenizeParameter("paramName", "paramValue", "paramType"));
        assertTokenize("doSomething(paramName);", parameterMap, t("Do something"), t("paramValue"));
    }

    /**
     * a1 = A 1
     * aB1 = A b 1
     * is10or13 = Is 10 or 13
     */
    @Test
    void handlesNumbersInNames() {
        assertTokenize("a1", t("A 1"));
        assertTokenize("aB1", t("A b 1"));
        assertTokenize("is10or13", t("Is 10 or 13"));
    }

    @Test
    void handlesAllCaps() {
        assertTokenize("AB", t("AB"));
        assertTokenize("AB_CD", t("AB_CD"));
    }

    /**
     * Maybe users will need an underscore
     * GivenIWant = Given I want
     * IThinkITIsGreat = I think IT is great
     * ITIsGreat = IT is great
     * IWantToWorkInIT = I want to work in IT
     */
    @Disabled
    @Test
    void handlesCasesAndAcronyms() {
        assertTokenize("aB", t("A"), t("b"));
        assertTokenize("aBCD", t("A"), t("BCD"));
        assertTokenize("aBCDEe", t("A"), t("BCDE"), t("e"));
    }

    @Disabled
    @Test
    void LearningTestForGeneralJavaCode() {
        String str1 = "roots.forEach(root -> logger.debug(\"tags: \" + root.getTags()));";
        String str2 = "logger.info(() -> \"message\");";
        String str3 = "givenBuilder.build().getBooks().forEach((isbn, book) -> {";
        String str4 = "String[] arr = {}";

        System.out.println(tokenizeSource(str1));
        System.out.println(tokenizeSource(str2));
        System.out.println(tokenizeSource(str3));
        System.out.println(tokenizeSource(str4));

        System.out.println("as string: " + tokenizeSource(str1).asString());
        System.out.println("as string: " + tokenizeSource(str2).asString());
        System.out.println("as string: " + tokenizeSource(str3).asString());
        System.out.println("as string: " + tokenizeSource(str4).asString());
    }

    private JavaSourceTokens tokenizeSource(String source) {
        return new TokenizeSource(source, new TokenizeParameterMap()).tokenize();
    }

    private JavaSourceTokens tokenizeSource(String source, TokenizeParameterMap parameterMap) {
        return new TokenizeSource(source, parameterMap).tokenize();
    }

    private void assertEmptyTokenListStartingAt(String source, int lineStart) {
        assertThat(new TokenizeSource(source, new TokenizeParameterMap()).tokenize()).isEqualTo(new JavaSourceTokens(Lists.emptyList(), lineStart));
    }

    private void assertTokenizeStartingAt(String source, int lineStart, Token... expected) {
        assertThat(new TokenizeSource(source, new TokenizeParameterMap()).tokenize()).isEqualTo(new JavaSourceTokens(asList(expected), lineStart));
    }

    private void assertTokenize(String source, Token... expected) {
        assertThat(new TokenizeSource(source, new TokenizeParameterMap()).tokenize()).isEqualTo(new JavaSourceTokens(asList(expected), 0));
    }

    private void assertTokenize(String source, TokenizeParameterMap parameterMap, Token... expected) {
        assertThat(new TokenizeSource(source, parameterMap).tokenize()).isEqualTo(new JavaSourceTokens(asList(expected), 0));
    }

    private Token t(String source) {
        return new Token(source, TokenType.DEFAULT);
    }

    private Token t(String source, TokenType type) {
        return new Token(source, type);
    }
}