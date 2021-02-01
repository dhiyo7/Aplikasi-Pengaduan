package dev7.id.sidausappspublic.Server;

import java.util.List;
import dev7.id.sidausappspublic.Model.Desa;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface DesaInterface {
    @GET("rest-api/desa/")
    Call<List<Desa>> getDesa (@Header("Authorization") String token);
}
