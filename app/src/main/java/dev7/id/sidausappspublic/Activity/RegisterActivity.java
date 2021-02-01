package dev7.id.sidausappspublic.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import dev7.id.sidausappspublic.Model.User;
import dev7.id.sidausappspublic.R;
import dev7.id.sidausappspublic.Server.ApiUtil;
import dev7.id.sidausappspublic.Server.RegisInterface;
import dev7.id.sidausappspublic.Server.UserInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private RegisInterface regisInterface = ApiUtil.getRegisInterface();
    private SharedPreferences setting;
    private TextInputEditText etUsername2, etPassword2, etEmail2;
    private Button btnRegis;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        doRegis();
        doLogin();
    }


    private void initView() {
        etUsername2 = findViewById(R.id.etUsername2);
        etPassword2 = findViewById(R.id.etPassword2);
        etEmail2 = findViewById(R.id.etEmail2);
        btnRegis = findViewById(R.id.btnRegis);
        tvLogin = findViewById(R.id.tvLogin);
        setting = getSharedPreferences("USER", MODE_PRIVATE);
    }

    private void doLogin() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(a);
            }
        });
    }

    private void doRegis() {
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername2.getText().toString().trim();
                String email = etEmail2.getText().toString().trim();
                String password = etPassword2.getText().toString().trim();
                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && password.length() > 3 && username.length() > 2){
                    btnRegis.setEnabled(false);
                    Call<User> _user = regisInterface.registerr(username, email, password);
                    _user.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User result = response.body();
                            System.out.println("bodyne "+response.body());
                            if (response.isSuccessful()){
//                                Log.i("done", result.toString());
//                                String  = response.body();
                                System.out.println("wew "+ response );
                                System.out.println("weok "+response.body());
                                setLoggedIn(result.getToken());
                                Intent a = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(a);
                                finish();
                            }else {
                                Toast.makeText(RegisterActivity.this, "Gagal Daftar", Toast.LENGTH_SHORT).show();
                                System.err.println("apaaan " + response);
                                Log.i("done", response.toString());
                                System.out.println("kuwuk "+response.body());
                            }
                            btnRegis.setEnabled(true);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            btnRegis.setEnabled(true);
                            System.err.println("mbuh " +t.getMessage());
                            Toast.makeText(RegisterActivity.this, "Daftar Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

    private void setLoggedIn(String token) {

        SharedPreferences.Editor editor = setting.edit();
        editor.putString("TOKEN", token);
        editor.commit();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isNotLoggedIn()){
            //jika user sydah login
            //finish();
            startActivity(new Intent(this, TestActivity.class));
            finish();
        }else{
            //nothing here

        }
    }

    private boolean isNotLoggedIn() {
        String token = setting.getString("TOKEN", "UNDIFINED");
        return token == null || token.equals("UNDIFINED");
    }
}
