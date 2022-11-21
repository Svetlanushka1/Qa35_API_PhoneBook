package okhttp3;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import org.testng.Assert;
import org.testng.annotations.Test;
import config.Provider;

import java.io.IOException;

public class LoginTests {



    @Test
    public void loginSuccess() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().username("noa@gmail.com").password("Nnoa12345$").build();

        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(auth),Provider.getInstance().getJson());
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();

        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);
    }
}
