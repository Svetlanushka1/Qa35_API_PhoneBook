package okhttp3;

import config.Provider;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDto;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class RegistrationTests {

    @Test
    public void registrationSuccess() throws IOException {
        int i = new Random().nextInt(1000)+1000;
        AuthRequestDto auth = AuthRequestDto.builder().username("no"+i+"@gmail.com").password("Nnoa12345$").build();

        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(auth),Provider.getInstance().getJson());
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        AuthResponseDto responseDto = Provider.getInstance().getGson().fromJson(response.body().string(),AuthResponseDto.class);
        System.out.println(responseDto.getToken());

    }
    @Test
    public void registrationWrongEmail() throws IOException {

        AuthRequestDto auth = AuthRequestDto.builder().username("liagmail.com").password("Nnoa12345$").build();

        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(auth),Provider.getInstance().getJson());
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = Provider.getInstance().getClient().newCall(request).execute();

        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);

        ErrorDto errorDto = Provider.getInstance().getGson().fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getStatus(),400);
        Assert.assertEquals(errorDto.getError(),"Bad Request");
        Object mess = errorDto.getMessage();
        System.out.println(mess);
       // Assert.assertEquals(mess,"must be a well-formed email address");
        Assert.assertTrue(mess.toString().contains( "must be a well-formed email address"));

// {username=must be a well-formed email address}   "must be a well-formed email address"

    }
}
