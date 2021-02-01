package dev7.id.pakhendrawan.Helper
import dev7.id.sidausappspublic.Model.Desa
import dev7.id.sidausappspublic.Model.JenisIzin
import dev7.id.sidausappspublic.Model.Kecamatan
import dev7.id.sidausappspublic.Model.Kepemilikan
import java.util.*

class Utils {
    companion object {
        fun getKepemilikans() : List<Kepemilikan>{
            return mutableListOf<Kepemilikan>().apply { 
                add(Kepemilikan("PE", "Perseorangan"))
                add(Kepemilikan("BH", "Berbadan Hukum"))
            }
        }

        fun getKepemilikanName(id : String) : String {
            val kepemilikans = getKepemilikans()
            for(k in kepemilikans){
                if(k.id.equals(id)){
                    return k.name
                }
            }
            return "undefined"
        }
        
        fun getJenisIzins() : List<JenisIzin>{
            return mutableListOf<JenisIzin>().apply{
                add(JenisIzin("00", "Izin Prinsip Pemanfaatan Ruang"))
                add(JenisIzin("01", "Izin Lingkungan"))
                add(JenisIzin("02", "Izin Lokasi"))
                add(JenisIzin("03", "Izin Mendirikan Bangunan"))
                add(JenisIzin("04", "Izin Penyelenggaraan Reklame"))
                add(JenisIzin("05", "Izin Usaha Jasa Kontruksi"))
                add(JenisIzin("06", "Izin Usaha Toko Modern"))
                add(JenisIzin("07", "Izin Mendirikan Klinik Pratama"))
                add(JenisIzin("08", "Izin Operasional Klinik Pratama"))
                add(JenisIzin("09", "Izin Mendirikan Klinik Utama"))
                add(JenisIzin("10", "Izin Operasional Klinik Utama"))
                add(JenisIzin("11", "Izin Mendirikan Rumah Sakit"))
                add(JenisIzin("12", "Izin Operasional Rumah Sakit dan Penetapan Kelas"))
                add(JenisIzin("13", "Izin Laboratorium Kesehatan"))
                add(JenisIzin("14", "Izin Apotik"))
                add(JenisIzin("15", "Izin Optikal"))
                add(JenisIzin("16", "Izin Toko Obat atau Pedagang Eceran Obat"))
                add(JenisIzin("17", "Izin Operasional Puskesmas"))
                add(JenisIzin("18", "Izin Unit Transfusi Darah"))
                add(JenisIzin("19", "Tanda Daftar Usaha Pariwisata"))
                add(JenisIzin("20", "Izin Pembuangan Limbah Cair"))
                add(JenisIzin("21", "Izin Penyimpanan Sementara Limbah B3"))
                add(JenisIzin("22", "Izin Pengumpulan Limbah B3 Skala Kabupaten"))
                add(JenisIzin("23", "Izin Trayek Angkutan Penumpang"))
                add(JenisIzin("24", "Izin Garasi"))
                add(JenisIzin("25", "Tanda Daftar Gudang"))
                add(JenisIzin("26", "Izin Perubahan Peruntukan Penggunaan Tanah"))
                add(JenisIzin("27", "Izin Operasional Menara Telekomunikasi"))
            }
        }

        fun getNameJenisIzin(id : String) : String {
            val jenisIzins = getJenisIzins()
            for(j in jenisIzins){
                if(j.id.equals(id)){
                    return j.name
                }
            }
            return "undefined"
        }

        fun getDesaName(id : Int, desas : List<Desa>) : String {
            for(desa in desas){
                if(desa.id == id){
                    return desa.desa
                }
            }
            return "undefined"
        }

        private fun getKecamatanIdFromDesa(id : Int, desas : List<Desa>) : Int {
            for(desa in desas){
                if(desa.id == id){
                    return desa.kecamatan
                }
            }
            return 0
        }

        fun getKecamatanName(id_desa: Int, desas: ArrayList<Desa>, kecs: ArrayList<Kecamatan>) : String {
            val id_kec = getKecamatanIdFromDesa(id_desa, desas)
            for(k in kecs){
                if(k.id == id_kec){
                    return k.kecamatan
                }
            }
            return "undefined"
        }
    }
}