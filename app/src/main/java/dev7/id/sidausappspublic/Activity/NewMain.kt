package dev7.id.sidausappspublic.Activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ScrollView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dev7.id.pakhendrawan.Helper.Utils
import dev7.id.sidausappspublic.Model.*
import dev7.id.sidausappspublic.R
import dev7.id.sidausappspublic.Server.ApiUtil
import kotlinx.android.synthetic.main.activity_new_main.*
import kotlinx.android.synthetic.main.content_new_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class NewMain : AppCompatActivity() , OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private var apiKecamatan = ApiUtil.getKecamatanInterface()
    private var apiDesa = ApiUtil.getDesaInterface()
    private var api= ApiUtil.getUsahaInterface()
    private var currentUsaha = Usaha()
    private var filteredDesa = mutableListOf<Desa>()
    private var desas = mutableListOf<Desa>()
    private var kecamatans = mutableListOf<Kecamatan>()
    private var jenisIzins = Utils.getJenisIzins()
    private var myLocation : Location? = null
    var mMap: GoogleMap? = null
    var mapFragment: SupportMapFragment? = null
    internal lateinit var customView: View
    internal lateinit var nestedScrollView: NestedScrollView
    private var alamat: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Buat Aduan"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        customView = findViewById<View>(R.id.customViews)
        nestedScrollView = findViewById(R.id.nstd_View)
        mapFragment = supportFragmentManager.findFragmentById(R.id.mapss) as SupportMapFragment
        mapFragment!!.getMapAsync(this)
        fetchKecamatan()
        postAduan()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun postAduan() {
        btnSimpan.setOnClickListener {
            val pj = etPj.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()
            val nama = etNama.text.toString().trim()
            val isi_aduan = etIsi.text.toString().trim()
            val jawaban = "-"
            val verify = "N"


            if (!pj.isEmpty() && !alamat.isEmpty() && !nama.isEmpty() && isi_aduan.isNotEmpty() && jawaban.isNotEmpty()) {
                if (pj.length < 2) {
                    etPj.error = "Masukan Penananggug Jawab dengan benar"
                    etPj.requestFocus()
                    return@setOnClickListener
                }
                if (alamat.length < 4) {
                    etAlamat.error = "Masukan Alamat dengan benar"
                    etAlamat.requestFocus()
                    return@setOnClickListener
                }
                if (nama.length < 2) {
                    etNama.error = "Masukan Nama Minimal 2 Karakter"
                    etNama.requestFocus()
                    return@setOnClickListener
                }

                if (filteredDesa[0].id == 0) {
                    Toast.makeText(this@NewMain, "Desa belum dipilih", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if(myLocation == null){
                    Toast.makeText(this@NewMain, "Cannot get location. Please check your permission", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                btnSimpan.isEnabled=false
                val ji = spinnerJenisIzin.selectedItem as JenisIzin
                val desa = spinnerDesa.selectedItem as Desa
                val req = api.postAduan("Token ${getToken()}", nama, pj, alamat, verify, ji.id, desa.id, isi_aduan, jawaban, myLocation?.latitude, myLocation?.longitude)
                req.enqueue(object : Callback<Aduan>{
                    override fun onFailure(call: Call<Aduan>, t: Throwable) {
                        Toast.makeText(this@NewMain, "Failure ${t.message}", Toast.LENGTH_LONG).show()
                        btnSimpan.isEnabled = true
                    }

                    override fun onResponse(call: Call<Aduan>, response: Response<Aduan>) {
                        if(response.isSuccessful){
                            Toast.makeText(this@NewMain, "Success", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@NewMain, AduanActivity::class.java))
                        }else{
                            Toast.makeText(this@NewMain, "Something went wrong", Toast.LENGTH_LONG).show()
                            btnSimpan.isEnabled = true
                        }
                    }
                })
            }
        }
    }


    private fun getToken(): String? {
        val SharedPreferences = this.getSharedPreferences("USER", Context.MODE_PRIVATE)
        return SharedPreferences.getString("TOKEN", "UNDIFINED")
    }

    private fun fetchKecamatan(){
        apiKecamatan.getKecamatan("Token ${getToken()}").enqueue(object : Callback<List<Kecamatan>> {
            override fun onFailure(call: Call<List<Kecamatan>>, t: Throwable) {
                println(t.message)
                Toast.makeText(this@NewMain, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Kecamatan>>, response: Response<List<Kecamatan>>) {
                if(response.isSuccessful){
                    kecamatans = (response.body() as List<Kecamatan>).toMutableList()
                    fetchDesa()
                }
            }
        })
    }

    private fun fetchDesa(){
        apiDesa.getDesa("Token ${getToken()}").enqueue(object : Callback<List<Desa>> {
            override fun onFailure(call: Call<List<Desa>>, t: Throwable) {
                println(t.message)
                Toast.makeText(this@NewMain, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Desa>>, response: Response<List<Desa>>) {
                if(response.isSuccessful){
                    desas = response.body() as MutableList<Desa>
//                    filterDesa(currentUsaha.desa.toInt())
                    spinnerKecamatanBehavior()
                    spinnerJenisIzinBehavior()
//                    spinnerKepemilikanBehavior()
                }
            }
        })
    }

    private fun spinnerKecamatanBehavior(){
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, kecamatans)
        spinnerKecamatan.adapter = adapter
        spinnerKecamatan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val kecamatan = adapterView.getItemAtPosition(i) as Kecamatan
                filterDesa(kecamatan.id)
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }

//        val desa = selectedDesa()
//        for (position in 0 until adapter.count) {
//            val x = adapter.getItem(position) as Kecamatan
//            if (x.id == desa.kecamatan) {
//                spinnerKecamatan.setSelection(position)
//                return
//            }
//        }
    }

    private fun filterDesa(id_kecamatan : Int){
        val temp = ArrayList<Desa>()
        for (ds in desas) {
            if (ds.kecamatan == id_kecamatan) {
                temp.add(ds)
            }
        }
        if (temp.isEmpty()) {
            temp.add(Desa(0, "Tidak ada desa", 0))
            spinnerDesa.isEnabled = false
        } else {
            spinnerDesa.isEnabled = true
        }
        filteredDesa = temp
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, filteredDesa)
        spinnerDesa.adapter = adapter
    }

//    private fun selectedDesa() : Desa{
//        var selectedDesa = Desa()
//        for(ds in desas){
//            if(ds.id == currentUsaha.desa.toInt()){
//                selectedDesa = ds
//                break
//            }
//        }
//        return selectedDesa
//    }

    private fun spinnerJenisIzinBehavior() {
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, jenisIzins)
        spinnerJenisIzin.adapter = adapter
//        for (position in 0 until adapter.count) {
//            val x = adapter.getItem(position) as JenisIzin
//            if (x.id.equals(currentUsaha.jenis)) {
//                spinnerJenisIzin.setSelection(position)
//                return
//            }
//        }
    }

    override fun onMapReady(p0: GoogleMap?) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            Toast.makeText(this@NewMain, "Anda harus mengizinkan akses lokasi", Toast.LENGTH_LONG).show()
//            return
//        }
        println("hehe onmapResady")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 666)
        } else {
            val mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@NewMain)
            mFusedLocationProviderClient.lastLocation.apply {
                addOnCompleteListener {
                    if(it.isSuccessful){
                        it.result?.let {
                            myLocation = it
                            mMap = p0
                            val point = LatLng(myLocation!!.latitude, myLocation!!.longitude)
                            mMap?.apply {
                                uiSettings?.isZoomControlsEnabled = true
                                uiSettings?.isMyLocationButtonEnabled = true
                                uiSettings?.isTiltGesturesEnabled = true
                                animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.Builder().target(point).zoom(15f).build()))
                                clear()
                                isMyLocationEnabled = true
                                mMap?.setOnMapClickListener(this@NewMain)
                            }
                        }
                    }
                }
                addOnFailureListener {
                    Toast.makeText(this@NewMain, "Tidak dapat mengambil lokasi", Toast.LENGTH_LONG).show()
                }
            }

        }

    }


    override fun onMapClick(p0: LatLng?) {
//        Toast.makeText(this@AduanActivity, "Location changet to lat : ${p0?.latitude} and lon : ${p0?.longitude}", Toast.LENGTH_LONG).show()
        val marker = MarkerOptions().position(LatLng(p0!!.latitude, p0.longitude))
        alamat = ApiUtil.getAddressSimple(p0!!.latitude, p0.longitude, this)
        val markers = MarkerOptions().position(LatLng(p0.latitude, p0.longitude))
                .title("Lokasi saya").snippet(alamat)
        mMap?.clear()
        mMap?.addMarker(marker)
        etAlamat.setText(alamat)
        Log.d("catatan", p0.latitude.toString() + "===" + alamat)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 666) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enable location permission", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
