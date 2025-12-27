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

package io.techthinking.flowbdd.examples.bookstore.bdd.builder_example;

import io.techthinking.flowbdd.examples.bookstore.bdd.builder_example.builders.bdd.GivenIsbnDbBuilder;
import io.techthinking.flowbdd.examples.bookstore.bdd.builder_example.builders.bdd.WhenIsbnDbBuilder;
import io.techthinking.flowbdd.examples.bookstore.bdd.builder_example.mappings.DefaultMappings;
import io.techthinking.flowbdd.examples.bookstore.bdd.builder_example.model.bdd.ThenGetBookByIsbnBuilder;
import io.techthinking.flowbdd.examples.bookstore.bdd.builder_example.model.bdd.ThenGetBookByIsbnErrorBuilder;
import io.techthinking.flowbdd.examples.bookstore.model.IsbnBook;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import io.techthinking.flowbdd.report.junit5.test.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getAllServeEvents;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.techthinking.flowbdd.report.mermaid.MessageBuilder.aMessage;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseBookStoreTest extends BaseTest {

	@Autowired
	private TestRestTemplate template;

    private ResponseEntity<String> response;

    private static final int PORT = 8080;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final DefaultMappings defaultMappings = new DefaultMappings(this);

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
        .options(wireMockConfig().port(PORT))
        .build();

    @BeforeEach
    void setUp() {
        sequenceDiagram()
            .addActor("User")
            .addParticipant("BookStore")
            .addParticipant("ISBNdb");
        defaultMappings.initDefaultMappings();
    }

    @Override
    public void doc() {
    }

    public void given(GivenIsbnDbBuilder givenBuilder) {
        givenBuilder.build().getBooks().forEach((isbn, book) -> {
            stubFor(get(urlEqualTo("/isbn-db/" + isbn))
                .withPort(PORT)
                .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(isbnBookAsString(book))));
        });
    }

    public void when(WhenIsbnDbBuilder whenBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(MediaType.APPLICATION_JSON));

        String isbn = whenBuilder.build().getIsbn();
        sequenceDiagram().add(aMessage().from("User").to("BookStore").text("/book/" + isbn));
        response = template.getForEntity("/book/" + isbn, String.class, headers);

        List<ServeEvent> allServeEvents = getAllServeEvents();
        allServeEvents.forEach(event -> {
            sequenceDiagram().add(aMessage().from("BookStore").to("ISBNdb").text(event.getRequest().getUrl()));
            sequenceDiagram().add(aMessage().from("ISBNdb").to("BookStore").text(event.getResponse().getBodyAsString()));
        }) ;
    }

    public void then(ThenGetBookByIsbnBuilder thenBuilder) {
        sequenceDiagram().add(aMessage().from("BookStore").to("User").text(response.getBody()));
        assertThat(isbnBook(response.getBody())).isEqualTo(thenBuilder.build().getBook());
    }

    public void then(ThenGetBookByIsbnErrorBuilder thenBuilder) {
        sequenceDiagram().add(aMessage().from("BookStore").to("User").text(response.getBody()));

        if (thenBuilder.build().getStatusCode() != null) {
            assertThat(response.getStatusCode()).isEqualTo(thenBuilder.build().getStatusCode());
        }
        if (thenBuilder.build().getErrorMessage() != null) {
            assertThat(response.getBody()).isEqualTo(thenBuilder.build().getErrorMessage());
        }
    }

    private IsbnBook isbnBook(String json) {
        try {
            return MAPPER.readValue(json, IsbnBook.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String isbnBookAsString(IsbnBook book) {
        try {
            return MAPPER.writeValueAsString(book);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
