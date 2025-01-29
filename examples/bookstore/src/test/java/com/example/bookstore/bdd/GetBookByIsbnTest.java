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

package com.example.bookstore.bdd;

import com.example.bookstore.model.IsbnBook;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import com.flowbdd.report.junit5.results.extension.FlowBDD;
import com.flowbdd.report.junit5.test.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getAllServeEvents;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.flowbdd.report.mermaid.MessageBuilder.aMessage;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * To get UML and docs extend BaseTest
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(FlowBDD.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class GetBookByIsbnTest extends BaseTest {

    @Override
    public void doc() {
        featureNotes("Working progress for example of usage Smart BDD");
    }

    @Autowired
    private TestRestTemplate template;
    private static final int PORT = 8080;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
        .options(wireMockConfig().port(PORT))
        .build();

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /** Watchmen graphic novel - ISBN 10: 1852860243 / ISBN 13: 9781852860240 */
    private static final String VALID_10_DIGIT_ISBN_FOR_BOOK_1 = "1852860243";
    private static final String VALID_13_DIGIT_ISBN_FOR_BOOK_1 = "9781852860240";
    private static final String VALID_10_DIGIT_ISBN_WITH_DASHES = "1-852860-24-3";
    private static final String VALID_10_DIGIT_ISBN_WITH_SPACES = "1 852860 24 3";
    private static final String VALID_ISBN_X = "0-201-63385-X";

    /** War of the worlds ISBN 10: 0141024186  ISBN 13: 9780141024189 */
    private static final String UNKNOWN_ISBN = "9780141024189";

    private static final String INVALID_1_DIGIT_ISBN = "1";
    private static final String INVALID_9_DIGIT_ISBN = "123456789";
    private static final String INVALID_11_DIGIT_ISBN = "12345678901";
    private static final String INVALID_12_DIGIT_ISBN = "123456789012";
    private static final String INVALID_14_DIGIT_ISBN = "12345678901234";
    private final IsbnBook BOOK_1 = new IsbnBook(VALID_13_DIGIT_ISBN_FOR_BOOK_1, "book 1 title", singletonList("book 1 author"));

    private ResponseEntity<String> response = null;

    @BeforeEach
    public void setupMocksAndResponse() {
        stubGetBookByIsbn(VALID_13_DIGIT_ISBN_FOR_BOOK_1, BOOK_1);
        stubGetBookToReturn(UNKNOWN_ISBN, NOT_FOUND.value());
        response = null;
    }

    @BeforeEach
    void setupUml() {
        sequenceDiagram()
            .addActor("User")
            .addParticipant("BookStore")
            .addParticipant("ISBNdb");
    }

    @Order(0)
    @Test
    public void getBookBy13DigitIsbn_returnsTheCorrectBook() {
        whenGetBookByIsbnIsCalledWith(VALID_13_DIGIT_ISBN_FOR_BOOK_1);
        thenTheResponseIsEqualTo(BOOK_1);
    }

    @Order(1)
    @Test
    public void getBookIsCalledWithUnknownIsbn_returnsBookNotFound() {
        whenGetBookByIsbnIsCalledWith(UNKNOWN_ISBN);
        thenTheResponseStatusIs(NOT_FOUND);
        thenTheResponseErrorMessageIs("No book found for ISBN: 9780141024189");
    }

    @Order(3)
    @ParameterizedTest(name = "#{index} - ISBN {0} not valid - returns warning")
    @ValueSource(strings = {
        INVALID_1_DIGIT_ISBN, INVALID_9_DIGIT_ISBN, INVALID_11_DIGIT_ISBN, INVALID_12_DIGIT_ISBN, INVALID_14_DIGIT_ISBN})
    public void getBookByIsbn_whenIsbnNumberIsNot10or13_return417StatusCode(String isbn) {
        whenGetBookByIsbnIsCalledWith(isbn);
        thenTheResponseStatusIs(EXPECTATION_FAILED);
        thenTheResponseErrorMessageIs("ISBN should be 10 or 13 digits. Spaces and dashes are allowed.");
    }

    @Order(4)
    @ParameterizedTest(name = "#{index} - ISBN {0} is valid 10 digit ISBN that is converted to a 13 digit ISBN - returns book 1")
    @ValueSource(strings = {
        VALID_10_DIGIT_ISBN_FOR_BOOK_1, VALID_10_DIGIT_ISBN_WITH_DASHES, VALID_10_DIGIT_ISBN_WITH_SPACES})
    public void getBookByAValid10DigitIsbnWithDifferentFormatting_returnsTheCorrectBook(String isbn) {
        whenGetBookByIsbnIsCalledWith(isbn);
        thenTheResponseIsEqualTo(BOOK_1);
    }

    @Order(5)
    @Test
    public void getBookBy10DigitIsbnThatIsConvertedTo13DigitIsbn_returnsTheCorrectBookBasedOn13DigitIsbn() {
        whenGetBookByIsbnIsCalledWith(VALID_10_DIGIT_ISBN_FOR_BOOK_1);
        thenTheResponseIsEqualTo(BOOK_1);
    }

    private void stubGetBookByIsbn(String isbn, IsbnBook book) {
        stubFor(get(urlEqualTo("/isbn-db/" + isbn))
            .withPort(PORT)
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(bookAsString(book))));
    }

    private void stubGetBookToReturn(String isbn, int status) {
        stubFor(get(urlEqualTo("/isbn-db/" + isbn))
            .withPort(PORT)
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withStatus(status)));
    }

    private String bookAsString(IsbnBook book) {
        try {
            return MAPPER.writeValueAsString(book);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private IsbnBook bookFromJson(String json) {
        try {
            return MAPPER.readValue(json, IsbnBook.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void thenTheResponseIsEqualTo(IsbnBook book) {
        assertThat(bookFromJson(response.getBody())).isEqualTo(book);
    }

    private void thenTheResponseStatusIs(HttpStatus statusCode) {
        assertThat(response.getStatusCode()).isEqualTo(statusCode);
    }

    private void thenTheResponseErrorMessageIs(String errorMessage) {
        assertThat(response.getBody()).isEqualTo(errorMessage);
    }

    private void whenGetBookByIsbnIsCalledWith(String isbn) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(MediaType.APPLICATION_JSON));
        response = template.getForEntity("/book/" + isbn, String.class, headers);
        generateSequenceDiagram(isbn, response, headers);
    }

    private void generateSequenceDiagram(String isbn, ResponseEntity<String> response, HttpHeaders headers) {
        sequenceDiagram().add(aMessage().from("User").to("BookStore").text("/book/" + isbn));


        List<ServeEvent> allServeEvents = getAllServeEvents();
        allServeEvents.forEach(event -> {
            sequenceDiagram().add(aMessage().from("BookStore").to("ISBNdb").text(event.getRequest().getUrl()));
            sequenceDiagram().add(aMessage().from("ISBNdb").to("BookStore").text(
                event.getResponse().getBodyAsString() +  " [" + event.getResponse().getStatus() + "]"));
        });

        sequenceDiagram().add(aMessage().from("BookStore").to("User").text(response.getBody() + " [" + response.getStatusCode().value() + "]"));
    }
}
