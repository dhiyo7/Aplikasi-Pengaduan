package dev7.id.sidausappspublic.Activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dev7.id.sidausappspublic.Helper.AdapterUsaha;
import dev7.id.sidausappspublic.Helper.PageAdapter;
import dev7.id.sidausappspublic.Model.Desa;
import dev7.id.sidausappspublic.Model.Kecamatan;
import dev7.id.sidausappspublic.Model.Usaha;
import dev7.id.sidausappspublic.R;
import dev7.id.sidausappspublic.Server.ApiUtil;
import dev7.id.sidausappspublic.Server.DesaInterface;
import dev7.id.sidausappspublic.Server.KecamatanInterface;
import dev7.id.sidausappspublic.Server.UsahaInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout emptyView, errorView;
    private RecyclerView rvUsaha;
    private UsahaInterface usahaInterface;
    private ProgressBar progressBar;
    private AdapterUsaha adapterUsaha;
    private ArrayList<Usaha> usahas = new ArrayList<>();
    private retrofit2.Call<List<Usaha>> call;
    private Call desaRequest;
    private Call kecamatanRequest;
    private ArrayList desas = new ArrayList();
    private ArrayList kecs = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        permissionAsk();
    }

    private void initView() {

        usahaInterface = ApiUtil.getUsahaInterface();
        progressBar = findViewById(R.id.prograss);
        rvUsaha = findViewById(R.id.rvUsaha);
        emptyView = findViewById(R.id.root_empty_view);
        errorView = findViewById(R.id.root_error_view);
        rvUsaha.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvUsaha.setHasFixedSize(true);

    }

    public void fetchData(){
        call=usahaInterface.getUsahaUnVerif("Token " +getToken());
        call.enqueue(new Callback<List<Usaha>>() {
            @Override
            public void onResponse(Call<List<Usaha>> call, Response<List<Usaha>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (response.isSuccessful() && response !=null){
                    usahas = new ArrayList<>(response.body());
                    adapterUsaha = new AdapterUsaha(usahas, desas, kecs, MainActivity.this);
                    rvUsaha.setAdapter(adapterUsaha);
                    if (response.body().isEmpty()){
                        rvUsaha.setVisibility(View.INVISIBLE);
                        emptyView.setVisibility(View.VISIBLE);
                        errorView.setVisibility(View.INVISIBLE);
                    }else {
                        rvUsaha.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.INVISIBLE);
                        errorView.setVisibility(View.INVISIBLE);
                    }
                }else {
                    rvUsaha.setVisibility(View.INVISIBLE);
                    emptyView.setVisibility(View.INVISIBLE);
                    errorView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Usaha>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                rvUsaha.setVisibility(View.INVISIBLE);
                emptyView.setVisibility(View.INVISIBLE);
                errorView.setVisibility(View.VISIBLE);
                System.out.println("errore kie" + t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchDesa();
    }

    @Override
    public void onDestroy() {
        if( call != null) { call.cancel(); }

        if (desaRequest != null ) { desaRequest.cancel(); }

        if (kecamatanRequest != null){ kecamatanRequest.cancel(); }
        super.onDestroy();
    }

    private final String getToken() {
        SharedPreferences SharedPreferences = MainActivity.this.getSharedPreferences("USER", MODE_PRIVATE);
        String token = SharedPreferences.getString("TOKEN", "UNDIFINED");
        return token;
    }

    private void fetchDesa(){
        DesaInterface di = ApiUtil.getDesaInterface();
        desaRequest = di.getDesa("Token "+getToken());
        desaRequest.enqueue(new Callback<List<Desa>>(){
            @Override
            public void onResponse(Call<List<Desa>> call, Response<List<Desa>> response) {
                if(response.isSuccessful()){
                    if(response != null){
                        desas = (ArrayList) response.body();
                        fetchKecamatan();
                        if (response.body().isEmpty()){
                            rvUsaha.setVisibility(View.INVISIBLE);
                            emptyView.setVisibility(View.VISIBLE);
                            errorView.setVisibility(View.INVISIBLE);
                        }else {
                            rvUsaha.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.INVISIBLE);
                            errorView.setVisibility(View.INVISIBLE);
                        }
                    }else {
                        rvUsaha.setVisibility(View.INVISIBLE);
                        emptyView.setVisibility(View.INVISIBLE);
                        errorView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Desa>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                rvUsaha.setVisibility(View.INVISIBLE);
                emptyView.setVisibility(View.INVISIBLE);
                errorView.setVisibility(View.VISIBLE);
                System.out.println("errore kie" + t.getMessage());
            }
        });
    }

    private void fetchKecamatan(){
        KecamatanInterface ki = ApiUtil.getKecamatanInterface();
        kecamatanRequest = ki.getKecamatan("Token "+getToken());
        kecamatanRequest.enqueue(new Callback<List<Kecamatan>>(){
            @Override
            public void onResponse(Call<List<Kecamatan>> call, Response<List<Kecamatan>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        kecs = (ArrayList) response.body();
                        fetchData();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Kecamatan>> call, Throwable t) {
                System.out.println("errore kie" + t.getMessage());
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void permissionAsk(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 666);
        } else {
            Toast.makeText(this, "Permission location must be granted", Toast.LENGTH_SHORT).show();
            System.out.println("Location permissions available, starting location");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 666){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Please enable location permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
