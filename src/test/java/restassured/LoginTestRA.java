package restassured;

import com.jayway.restassured.RestAssured;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDto;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LoginTestRA {

    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void loginSuccess(){
        AuthRequestDto auth = AuthRequestDto.builder().username("noa@gmail.com").password("Nnoa12345$").build();

       AuthResponseDto responseDto =  given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response()
                .as(AuthResponseDto.class);
        System.out.println(responseDto.getToken());
    }
    @Test
    public void loginWrongEmail(){
        AuthRequestDto auth = AuthRequestDto.builder().username("noagmail.com").password("Nnoa12345$").build();

       ErrorDto errorDto =  given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(401)
                .extract()
                .response()
                .as(ErrorDto.class);
        Object message  = errorDto.getMessage();
        Assert.assertEquals(message,"Login or Password incorrect");
        Assert.assertEquals(errorDto.getStatus(),401);

    }
    @Test
    public void loginWrongEmailFormat(){
        AuthRequestDto auth = AuthRequestDto.builder().username("noagmail.com").password("Nnoa12345$").build();

       given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(401)
               .assertThat().body("message",containsString("Login or Password incorrect"))
               .assertThat().body("status",equalTo(401));


    }
}
