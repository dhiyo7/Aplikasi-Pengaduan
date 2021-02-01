package dev7.id.sidausappspublic.Model;

import com.google.gson.annotations.SerializedName;

public class Kecamatan {
    @SerializedName("token") private String token;
    @SerializedName("id") private int id;
    @SerializedName("Kecamatan") private String Kecamatan;

    public Kecamatan() {
    }

    public Kecamatan(String token, int id, String kecamatan) {
        this.token = token;
        this.id = id;
        Kecamatan = kecamatan;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKecamatan() {
        return Kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        Kecamatan = kecamatan;
    }

    @Override
    public String toString() {
        return getKecamatan();
    }
}
