package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class AddNewContactTestsRA {
    String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibm9hQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNjY5NjU0NDI4LCJpYXQiOjE2NjkwNTQ0Mjh9.MD1m2cTr7MFMbDrSxECMAIviWQ5mnNAGQPY-cFdExWI";

    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void addContactSuccess() {
        int i = new Random().nextInt(1000) + 1000;

        ContactDto contact = ContactDto.builder()
                .name("Anna" + i)
                .lastName("Fox" + i)
                .email("anna" + i + "@ukr.net")
                .phone("0534445" + i)
                .address("Tel Aviv")
                .description("friend")
                .build();
        given()
                .header("Authorization",token)
                .body(contact)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message",containsString("Contact was added!"));
    }

}
