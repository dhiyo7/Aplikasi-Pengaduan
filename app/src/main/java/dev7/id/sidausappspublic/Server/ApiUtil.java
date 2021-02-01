package dev7.id.sidausappspublic.Server;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.android.volley.VolleyLog.TAG;

public class ApiUtil {
    public static final String BASE_URL = "YOUR_BASE_URL_ENDPOINT";

    private static Retrofit retrofit;

    private static Retrofit getApiClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static UserInterface getUserInterface() { return getApiClient().create(UserInterface.class); }
    public static RegisInterface getRegisInterface() { return getApiClient().create(RegisInterface.class); }
    public static KecamatanInterface getKecamatanInterface() { return getApiClient().create(KecamatanInterface.class); }
    public static DesaInterface getDesaInterface() { return getApiClient().create(DesaInterface.class); }
    public static UsahaInterface getUsahaInterface() { return getApiClient().create(UsahaInterface.class); }

    public static String getAddressSimple(Double late, Double longe, Context context) {
        String addressStr = "";
        Geocoder geocoder;
        List<Address> yourAddresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            yourAddresses = geocoder.getFromLocation(late,longe, 1);
            if (yourAddresses.size() > 0) {
                addressStr += yourAddresses.get(0).getAddressLine(0);
//                addressStr += yourAddresses.get(0).getAddressLine(1);
//                addressStr += yourAddresses.get(0).getAddressLine(2);
                Log.d(TAG, "getAddressSimple: " +addressStr);
            } else {
                addressStr = "Nama jalan tidak diketahui...";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressStr;
    }

}
