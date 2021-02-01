package dev7.id.sidausappspublic.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import dev7.id.sidausappspublic.Helper.AdapterAduan;
import dev7.id.sidausappspublic.Model.Aduan;
import dev7.id.sidausappspublic.Model.Desa;
import dev7.id.sidausappspublic.Model.Kecamatan;
import dev7.id.sidausappspublic.R;
import dev7.id.sidausappspublic.Server.ApiUtil;
import dev7.id.sidausappspublic.Server.DesaInterface;
import dev7.id.sidausappspublic.Server.KecamatanInterface;
import dev7.id.sidausappspublic.Server.UsahaInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AduanActivity extends AppCompatActivity {

    private RelativeLayout emptyView, errorView;
    private RecyclerView rvAduan;
    private UsahaInterface usahaInterface;
    private ProgressBar progressBar;
    private AdapterAduan adapterAduan;
    private ArrayList<Aduan> usahas = new ArrayList<>();
    private Call<List<Aduan>> call;
    private Call desaRequest;
    private Call kecamatanRequest;
    private ArrayList desas = new ArrayList();
    private ArrayList kecs = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aduan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Daftar Aduan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true
        );

        initView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void initView() {
        usahaInterface = ApiUtil.getUsahaInterface();
        progressBar = findViewById(R.id.prograss);
        rvAduan = findViewById(R.id.rvAduan);
        emptyView = findViewById(R.id.root_empty_view);
        errorView = findViewById(R.id.root_error_view);
        rvAduan.setLayoutManager(new LinearLayoutManager(AduanActivity.this));
        rvAduan.setHasFixedSize(true);
    }

    public void fetchData(){
        call=usahaInterface.getAduan("Token "+getToken());
        call.enqueue(new Callback<List<Aduan>>() {
            @Override
            public void onResponse(Call<List<Aduan>> call, Response<List<Aduan>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (response.isSuccessful() && response !=null){
                    usahas = new ArrayList<Aduan>(response.body());
                    adapterAduan = new AdapterAduan(usahas, desas, kecs, AduanActivity.this);
                    rvAduan.setAdapter(adapterAduan);
                    if (response.body().isEmpty()){
                        rvAduan.setVisibility(View.INVISIBLE);
                        emptyView.setVisibility(View.VISIBLE);
                        errorView.setVisibility(View.INVISIBLE);
                    }else {
                        rvAduan.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.INVISIBLE);
                        errorView.setVisibility(View.INVISIBLE);
                    }
                }else {
                    rvAduan.setVisibility(View.INVISIBLE);
                    emptyView.setVisibility(View.INVISIBLE);
                    errorView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Aduan>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                rvAduan.setVisibility(View.INVISIBLE);
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
                            rvAduan.setVisibility(View.INVISIBLE);
                            emptyView.setVisibility(View.VISIBLE);
                            errorView.setVisibility(View.INVISIBLE);
                        }else {
                            rvAduan.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.INVISIBLE);
                            errorView.setVisibility(View.INVISIBLE);
                        }
                    }else {
                        rvAduan.setVisibility(View.INVISIBLE);
                        emptyView.setVisibility(View.INVISIBLE);
                        errorView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Desa>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                rvAduan.setVisibility(View.INVISIBLE);
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

    private final String getToken() {
        SharedPreferences SharedPreferences = AduanActivity.this.getSharedPreferences("USER", MODE_PRIVATE);
        String token = SharedPreferences.getString("TOKEN", "UNDIFINED");
        return token;
    }

}
