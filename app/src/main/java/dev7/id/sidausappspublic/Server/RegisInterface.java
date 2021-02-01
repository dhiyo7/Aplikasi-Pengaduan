package dev7.id.sidausappspublic.Server;

import dev7.id.sidausappspublic.Model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegisInterface {

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("register/")
    Call<User> registerr (@Field("username") String username, @Field("email") String email, @Field("password") String password);
}
