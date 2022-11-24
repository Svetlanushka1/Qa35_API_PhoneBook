package restassured;

import com.jayway.restassured.RestAssured;
import dto.ContactDto;
import dto.GetAllContactsDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;

public class GetAllContactsRA {

    String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibm9hQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNjY5NjU0NDI4LCJpYXQiOjE2NjkwNTQ0Mjh9.MD1m2cTr7MFMbDrSxECMAIviWQ5mnNAGQPY-cFdExWI";

    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void getAllContactsSuccess(){
       GetAllContactsDto contactsDto =  given()
                .header("Authorization",token)
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response()
                .as(GetAllContactsDto.class);

        List<ContactDto> list = contactsDto.getContacts();
        for (ContactDto contactDto:list) {
            System.out.println(contactDto.getId());
            System.out.println("**************");
        }

    }
    @Test
    public void getAllContactsUnauthorized(){
        given()
                .header("Authorization","nbgtdes")
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(401);
    }

}
