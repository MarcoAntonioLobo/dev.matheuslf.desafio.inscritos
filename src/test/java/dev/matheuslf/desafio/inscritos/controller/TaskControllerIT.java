package dev.matheuslf.desafio.inscritos.controller;

import static io.restassured.RestAssured.given;
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
import dev.matheuslf.desafio.inscritos.enums.TaskPriority;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class TaskControllerIT {

    @LocalServerPort
    private int port;

    private UUID projectId;

    @BeforeEach
    void setup() {
        RestAssured.port = port;

        // Cria projeto para relacionar a task
        String projectPayload = """
            {
                "name": "Projeto para Task",
                "description": "Projeto de integração Task",
                "startDate": "2025-10-22",
                "endDate": "2025-10-29"
            }
        """;

        projectId = UUID.fromString(
            given()
                .auth().basic("admin", "123")
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
    void createTask_shouldReturnCreated() {
        TaskCreateDTO dto = new TaskCreateDTO(
                "Nova Tarefa",
                "Descrição da tarefa",
                TaskPriority.HIGH,
                LocalDate.now().plusDays(5),
                projectId
        );

        given()
            .auth().basic("admin", "123")
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/tasks")
        .then()
            .statusCode(201)
            .body("title", equalTo("Nova Tarefa"))
            .body("description", equalTo("Descrição da tarefa"))
            .body("priority", equalTo("HIGH"))
            .body("projectId", equalTo(projectId.toString()));
    }

    @Test
    void createTask_withoutAuth_shouldReturnUnauthorized() {
        TaskCreateDTO dto = new TaskCreateDTO(
                "Tarefa Sem Auth",
                "Descrição",
                TaskPriority.LOW,
                LocalDate.now().plusDays(5),
                projectId
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/tasks")
        .then()
            .statusCode(401);
    }

    @Test
    void createTask_withInvalidProject_shouldReturnNotFound() {
        TaskCreateDTO dto = new TaskCreateDTO(
                "Tarefa Invalida",
                "Projeto inexistente",
                TaskPriority.MEDIUM,
                LocalDate.now().plusDays(5),
                UUID.randomUUID()
        );

        given()
            .auth().basic("admin", "123")
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/tasks")
        .then()
            .statusCode(404);
    }

    @Test
    void listTasks_shouldReturnOk() {
        given()
            .auth().basic("admin", "123")
            .accept(ContentType.JSON)
        .when()
            .get("/tasks")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    void listTasks_withoutAuth_shouldReturnUnauthorized() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/tasks")
        .then()
            .statusCode(401);
    }
}
