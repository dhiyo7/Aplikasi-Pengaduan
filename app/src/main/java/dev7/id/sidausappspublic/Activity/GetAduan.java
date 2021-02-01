package dev7.id.sidausappspublic.Activity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import dev7.id.sidausappspublic.Helper.AdapterUsaha;
import dev7.id.sidausappspublic.Model.Usaha;
import dev7.id.sidausappspublic.R;
import dev7.id.sidausappspublic.Server.ApiUtil;
import dev7.id.sidausappspublic.Server.UsahaInterface;
import retrofit2.Call;

public class GetAduan extends AppCompatActivity {

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
        setContentView(R.layout.activity_get_ad_uan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
    }

    private void initView() {
        usahaInterface = ApiUtil.getUsahaInterface();
        progressBar = findViewById(R.id.prograss);
        rvUsaha = findViewById(R.id.rvUsaha);
        emptyView = findViewById(R.id.root_empty_view);
        errorView = findViewById(R.id.root_error_view);
        rvUsaha.setLayoutManager(new LinearLayoutManager(GetAduan.this));
        rvUsaha.setHasFixedSize(true);
    }



}
