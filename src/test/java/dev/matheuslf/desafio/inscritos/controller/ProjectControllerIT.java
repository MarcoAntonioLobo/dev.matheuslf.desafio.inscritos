package dev.matheuslf.desafio.inscritos.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ProjectControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void createProject_shouldReturnCreated() {
        String payload = """
            {
                "name": "Projeto Teste RestAssured",
                "description": "Teste de integração",
                "startDate": "2025-10-22",
                "endDate": "2025-10-29"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/projects")
        .then()
            .statusCode(201)
            .body("name", equalTo("Projeto Teste RestAssured"))
            .body("description", equalTo("Teste de integração"));
    }
}
