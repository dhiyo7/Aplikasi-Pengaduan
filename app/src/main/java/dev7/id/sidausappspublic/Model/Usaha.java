package dev7.id.sidausappspublic.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Usaha implements Parcelable {
    @SerializedName("id") private int id;
    @SerializedName("token") private String token;
    @SerializedName("NamaPenanggungJawab") private String NamaPenanggungJawab;
    @SerializedName("Alamat") private String Alamat;
    @SerializedName("Nama") private String Nama;
    @SerializedName("Latitude") private String Latitude;
    @SerializedName("Longitude") private String Longitude;
    @SerializedName("Telpon") private String Telpon;
    @SerializedName("Verify") private String Verify;
    @SerializedName("Jenis") private String Jenis;
    @SerializedName("Kepemilikan") private String Kepemilikan;
    @SerializedName("Desa") private String Desa;
    @SerializedName("IsiAduan") private String IsiAduan;
    @SerializedName("Jawaban") private String Jawaban;


    public Usaha() {
    }

    public Usaha(int id, String token, String namaPenanggungJawab, String alamat, String nama, String latitude, String longitude, String telpon, String verify, String jenis, String kepemilikan, String desa, String isiAduan, String jawaban) {
        this.id = id;
        this.token = token;
        NamaPenanggungJawab = namaPenanggungJawab;
        Alamat = alamat;
        Nama = nama;
        Latitude = latitude;
        Longitude = longitude;
        Telpon = telpon;
        Verify = verify;
        Jenis = jenis;
        Kepemilikan = kepemilikan;
        Desa = desa;
        IsiAduan = isiAduan;
        Jawaban = jawaban;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNamaPenanggungJawab() {
        return NamaPenanggungJawab;
    }

    public void setNamaPenanggungJawab(String namaPenanggungJawab) {
        NamaPenanggungJawab = namaPenanggungJawab;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getTelpon() {
        return Telpon;
    }

    public void setTelpon(String telpon) {
        Telpon = telpon;
    }

    public String getVerify() {
        return Verify;
    }

    public void setVerify(String verify) {
        Verify = verify;
    }

    public String getJenis() {
        return Jenis;
    }

    public void setJenis(String jenis) {
        Jenis = jenis;
    }

    public String getKepemilikan() {
        return Kepemilikan;
    }

    public void setKepemilikan(String kepemilikan) {
        Kepemilikan = kepemilikan;
    }

    public String getDesa() {
        return Desa;
    }

    public void setDesa(String desa) {
        Desa = desa;
    }

    public String getIsiAduan() {
        return IsiAduan;
    }

    public void setIsiAduan(String isiAduan) {
        IsiAduan = isiAduan;
    }

    public String getJawaban() {
        return Jawaban;
    }

    public void setJawaban(String jawaban) {
        Jawaban = jawaban;
    }

    protected Usaha(Parcel in) {
        id = in.readInt();
        token = in.readString();
        NamaPenanggungJawab = in.readString();
        Alamat = in.readString();
        Nama = in.readString();
        Latitude = in.readString();
        Longitude = in.readString();
        Telpon = in.readString();
        Verify = in.readString();
        Jenis = in.readString();
        Kepemilikan = in.readString();
        Desa = in.readString();
        IsiAduan = in.readString();
        Jawaban = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(token);
        dest.writeString(NamaPenanggungJawab);
        dest.writeString(Alamat);
        dest.writeString(Nama);
        dest.writeString(Latitude);
        dest.writeString(Longitude);
        dest.writeString(Telpon);
        dest.writeString(Verify);
        dest.writeString(Jenis);
        dest.writeString(Kepemilikan);
        dest.writeString(Desa);
        dest.writeString(IsiAduan);
        dest.writeString(Jawaban);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Usaha> CREATOR = new Parcelable.Creator<Usaha>() {
        @Override
        public Usaha createFromParcel(Parcel in) {
            return new Usaha(in);
        }

        @Override
        public Usaha[] newArray(int size) {
            return new Usaha[size];
        }
    };
}