package dev7.id.sidausappspublic.Server;

import java.util.List;
import dev7.id.sidausappspublic.Model.Kecamatan;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface KecamatanInterface {
    @GET("rest-api/kecamatan/")
    Call<List<Kecamatan>> getKecamatan(@Header("Authorization") String token);
}
