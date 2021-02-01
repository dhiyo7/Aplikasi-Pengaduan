package dev7.id.sidausappspublic.Model

import com.google.gson.annotations.SerializedName

data class Aduan(
        @SerializedName("id") var id : String? = null,
        @SerializedName("Nama") var nama : String? = null,
        @SerializedName("NamaPenanggungJawab") var penanggung_jawab : String? = null,
        @SerializedName("Alamat") var alamat : String? = null,
        @SerializedName("Latitude") var latitude : String? = null,
        @SerializedName("Longitude") var longitude : String? = null,
        @SerializedName("Jenis") var jenis_usaha : String? = null,
        @SerializedName("Desa") var desa : Int? = null,
        @SerializedName("IsiAduan") var isi_aduan : String? = null,
        @SerializedName("Jawaban") var jawaban : String? = null
) {
}