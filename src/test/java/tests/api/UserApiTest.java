package tests.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Pruebas de API REST automatizadas en Java utilizando REST Assured.
 * 
 * BDD Pattern:
 * given() -> Precondiciones (Headers, Body, Auth, Query Params)
 * when()  -> Acción HTTP (GET, POST, PUT, DELETE)
 * then()  -> Validaciones / Assertions (Status Code, Body, Response Headers, Response Time)
 */
public class UserApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    @DisplayName("GET /users - Obtener lista de usuarios (Status 200 OK)")
    public void testGetUsersListList() {
        given()
            .header("User-Agent", "Mozilla/5.0")
            .contentType(ContentType.JSON)
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("", hasSize(greaterThan(0)))
            .body("[0].email", containsString("@"));
    }

    @Test
    @DisplayName("POST /users - Crear nuevo usuario (Status 201 Created)")
    public void testCreateUser() {
        String requestBody = """
            {
                "name": "Morpheus",
                "username": "morpheus_leader",
                "email": "morpheus@matrix.com"
            }
            """;

        given()
            .header("User-Agent", "Mozilla/5.0")
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("/users")
        .then()
            .statusCode(201)
            .body("name", equalTo("Morpheus"))
            .body("username", equalTo("morpheus_leader"))
            .body("id", notNullValue());
    }

    @Test
    @DisplayName("GET /users/1 - Obtener usuario por ID (Status 200 OK)")
    public void testGetSingleUser() {
        given()
            .header("User-Agent", "Mozilla/5.0")
            .contentType(ContentType.JSON)
        .when()
            .get("/users/1")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("name", equalTo("Leanne Graham"))
            .body("email", equalTo("Sincere@april.biz"));
    }

    @Test
    @DisplayName("GET /users/999 - Usuario no encontrado (Status 404 Not Found)")
    public void testSingleUserNotFound() {
        given()
            .header("User-Agent", "Mozilla/5.0")
            .contentType(ContentType.JSON)
        .when()
            .get("/users/999")
        .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("PUT /users/1 - Actualizar usuario existente (Status 200 OK)")
    public void testUpdateUser() {
        String updateBody = """
            {
                "name": "Morpheus",
                "username": "zion_resident",
                "email": "morpheus@zion.com"
            }
            """;

        given()
            .header("User-Agent", "Mozilla/5.0")
            .contentType(ContentType.JSON)
            .body(updateBody)
        .when()
            .put("/users/1")
        .then()
            .statusCode(200)
            .body("username", equalTo("zion_resident"));
    }

    @Test
    @DisplayName("DELETE /users/1 - Eliminar usuario por ID (Status 200 OK)")
    public void testDeleteUser() {
        given()
            .header("User-Agent", "Mozilla/5.0")
            .contentType(ContentType.JSON)
        .when()
            .delete("/users/1")
        .then()
            .statusCode(200);
    }
}
