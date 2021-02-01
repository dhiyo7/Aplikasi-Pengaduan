package dev7.id.sidausappspublic.Server;
import dev7.id.sidausappspublic.Model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserInterface {

    @FormUrlEncoded
    @POST("api_token_auth/")
    Call<User> login (@Field("username") String username, @Field("password") String password);


}
