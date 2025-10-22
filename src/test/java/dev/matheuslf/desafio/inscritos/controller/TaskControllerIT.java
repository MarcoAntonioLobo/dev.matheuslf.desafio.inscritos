package dev.matheuslf.desafio.inscritos.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import dev.matheuslf.desafio.inscritos.dto.TaskCreateDTO;
import dev.matheuslf.desafio.inscritos.entity.TaskPriority;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class TaskControllerIT {

    @LocalServerPort
    private int port;

    private UUID projectId;

    @BeforeEach
    void setUp() {

        RestAssured.port = port;

        String projectPayload = """
            {
                "name": "Projeto Teste RestAssured",
                "description": "Projeto criado para testes",
                "startDate": "2025-10-22",
                "endDate": "2025-10-29"
            }
        """;

        projectId = UUID.fromString(
            RestAssured.given()
                .contentType(ContentType.JSON)
                .body(projectPayload)
            .when()
                .post("/projects")
            .then()
                .statusCode(201)
                .extract()
                .path("id")
        );
    }

    @Test
    void createTask_shouldReturnCreatedTask() {

        TaskCreateDTO dto = new TaskCreateDTO(
                "Nova Tarefa",
                "Descrição da tarefa",
                TaskPriority.HIGH,
                LocalDate.now().plusDays(5),
                projectId
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
        .when()
                .post("/tasks")
        .then()
                .statusCode(201) // Created
                .body("title", equalTo("Nova Tarefa"))
                .body("description", equalTo("Descrição da tarefa"))
                .body("priority", equalTo("HIGH"))
                .body("projectId", equalTo(projectId.toString()));
    }

    @Test
    void getTasks_shouldReturnList() {
        RestAssured.given()
                .accept(ContentType.JSON)
        .when()
                .get("/tasks")
        .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(0));
    }
}
