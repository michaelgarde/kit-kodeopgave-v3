package dk.mgarde;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class PersonResourceTest {

    @Test
    @Order(1)
    public void testGetAllPersonsEndpointWithNoData() {
        given()
          .when().get("/person")
          .then()
             .statusCode(404);
    }

    @Test
    @Order(2)
    public void testGetPersonByName() {
        given()
            .queryParam("name", "Someone")
            .when().get("/person/search")
            .then()
                .statusCode(404);
    }

    @Test
    @Order(3)
    public void testGetPersonById() {
        given()
            .queryParam("personId", "1")
            .when().get("/person/search")
            .then()
                .statusCode(404);
    }

    @Test
    @Order(4)
    public void addPerson() throws IOException {
        File person = new File("src/test/java/dk/mgarde/resources/person.json");
        given()
            .body(person).contentType(ContentType.JSON)
            .when().post("/person")
            .then()
                .statusCode(200);
    }

    @Test
    @Order(4)
    public void deletePerson() throws IOException {
        File person = new File("src/test/java/dk/mgarde/resources/person.json");
        given()
            .body(person).contentType(ContentType.JSON)
            .when().post("/person")
            .then()
                .statusCode(200);
        given()
            .queryParam("personId", "1")
            .when().delete("/person")
            .then()
                .statusCode(200);
    }
}