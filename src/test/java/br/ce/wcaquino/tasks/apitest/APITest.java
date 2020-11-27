package br.ce.wcaquino.tasks.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;

public class APITest {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas(){
        RestAssured
                .given()
                    //.log().all()
                .when()
                    .get("/todo")
                .then()
                    //.log().all()
                    .statusCode(200);
    }

    @Test
    public void deveAdicionarTarefaComSucesso(){
        int proximoAno = LocalDate.now().plusYears(1).getYear();
        String body = String.format("{\"task\":\"Teste via API\",\"dueDate\":\"%s-01-01\"}", proximoAno);

        RestAssured
                .given()
                    .body(body)
                    .contentType(ContentType.JSON)
                .when()
                    .post("/todo")
                .then()//.log().all()
                    .statusCode(201);
    }

    @Test
    public void naoDeveAdicionarTarefaInvalida(){
        RestAssured
                .given()
                    .body("{\"task\":\"Teste via API\",\"dueDate\":\"2000-12-31\"}")
                    .contentType(ContentType.JSON)
                .when()
                    .post("/todo")
                .then()
                    .statusCode(400)
                    .body("message", CoreMatchers.is("Due date must not be in past"));
    }

}