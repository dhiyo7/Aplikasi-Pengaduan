package dev7.id.sidausappspublic.Server;

import java.util.List;

import dev7.id.sidausappspublic.Model.Aduan;
import dev7.id.sidausappspublic.Model.Usaha;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsahaInterface {
    @GET("rest-api/usaha/")
    Call<List<Usaha>> getUsaha(@Header("Authorization") String token);

    @GET("rest-api/usaha/?Verify=Y")
    Call<List<Usaha>> getUsahaVerif(@Header("Authorization") String token);

    @GET("rest-api/usaha/?Verify=N")
    Call<List<Usaha>> getUsahaUnVerif(@Header("Authorization") String token);

    @GET("rest-api/usaha/{id}")
    Call<Usaha> getUsahabyID(@Header("Authorization") String token, @Path("id") int id);

    @FormUrlEncoded
    @POST("rest-api/usaha/")
    Call<Usaha> postUsaha(@Header("Authorization") String token,
                          @Field("Nama") String Nama,
                          @Field("NamaPenanggungJawab") String NamaPenanggungJawab,
                          @Field("Alamat") String Alamat,
                          @Field("Telpon") String Telepon,
                          @Field("Verify") String Verify,
                          @Field("Jenis") String Jenis,
                          @Field("Kepemilikan") String Kepemilikan,
                          @Field("Desa") int Desa);

    @FormUrlEncoded
    @PUT("rest-api/usaha/{id}/")
    Call<Usaha> putUsaha(@Header("Authorization") String token,
                         @Path("id") int id,
                         @Field("Nama") String Nama,
                         @Field("NamaPenanggungJawab") String NamaPenanggungJawab,
                         @Field("Alamat") String Alamat,
                         @Field("Telpon") String Telepon,
                         @Field("Verify") String Verify,
                         @Field("Jenis") String Jenis,
                         @Field("Kepemilikan") String Kepemilikan,
                         @Field("Desa") int Desa);

    @GET("rest-api/Aduan/")
    Call<List<Aduan>> getAduan(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("rest-api/Aduan/")
    Call<Aduan> postAduan(@Header("Authorization") String token,
                          @Field("Nama") String Nama,
                          @Field("NamaPenanggungJawab") String NamaPenanggungJawab,
                          @Field("Alamat") String Alamat,
                          @Field("Verify") String Verify,
                          @Field("Jenis") String Jenis,
                          @Field("Desa") int Desa,
                          @Field("IsiAduan") String IsiAduan,
                          @Field("Jawaban") String Jawaban,
                          @Field("Latitude") Double latitude,
                          @Field("Longitude") Double longitude);
}
