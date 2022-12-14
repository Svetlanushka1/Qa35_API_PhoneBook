package okhttp3;

import config.Provider;
import dto.ContactDto;
import dto.ContactDtoResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class AddNewContact {
    String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibm9hQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNjY5NjU0NDI4LCJpYXQiOjE2NjkwNTQ0Mjh9.MD1m2cTr7MFMbDrSxECMAIviWQ5mnNAGQPY-cFdExWI";


    @Test
    public void addContactSuccess() throws IOException {
        int i = new Random().nextInt(1000)+1000;

        ContactDto contact = ContactDto.builder()
                .name("Anna"+i)
                .lastName("Fox"+i)
                .email("anna"+i+"@ukr.net")
                .phone("0534445"+i)
                .address("Tel Aviv")
                .description("friend")
                .build();

        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(contact),Provider.getInstance().getJson());
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization",token)
                .post(body).build();
        Response response = Provider.getInstance().getClient().newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());
        ContactDtoResponse contactDtoResponse = Provider.getInstance().getGson().fromJson(response.body().string(),ContactDtoResponse.class);

        Assert.assertTrue(contactDtoResponse.getMessage().contains("Contact was added!"));
        // Contact was added! ID: 761491f5-4014-44b5-a2a7-c97278710a04
        String mess = contactDtoResponse.getMessage();
        String [] all =mess.split("ID: ");
        String  id = all[1];
        System.out.println(id);
    }
}
