package com.example.producer;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.providers.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.providers.connectors.InMemorySink;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.Any;
import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@QuarkusTestResource(InMemoryKafka.class)
public class ExampleResourceTest {

    private InMemorySink<Object> sink;

    @BeforeAll
    static void beforeAll() {
        InMemoryConnector.switchIncomingChannelsToInMemory("time-out");
    }

    @BeforeEach
    void setUp() {
        sink = connector.sink("time-out");
    }

    @Inject
    @Any
    InMemoryConnector connector;

    @Test
    public void testHelloEndpoint() {

        given()
                .when().get("/hello")
                .then()
                .statusCode(200)
                .body(containsString("Hello RESTEasy"));

        assertEquals(1, sink.received().size());
    }
}