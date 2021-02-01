package dev7.id.sidausappspublic.Model;

import com.google.gson.annotations.SerializedName;

public class Desa {
    @SerializedName("id") private int id;
    @SerializedName("Desa") private String Desa;
    @SerializedName("Kecamatan") private int Kecamatan;

    public Desa() {
    }

    public Desa(int id, String desa, int kecamatan) {
        this.id = id;
        Desa = desa;
        Kecamatan = kecamatan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesa() {
        return Desa;
    }

    public void setDesa(String desa) {
        Desa = desa;
    }

    public int getKecamatan() {
        return Kecamatan;
    }

    public void setKecamatan(int kecamatan) {
        Kecamatan = kecamatan;
    }

    @Override
    public String toString() {
        return getDesa();
    }
}
