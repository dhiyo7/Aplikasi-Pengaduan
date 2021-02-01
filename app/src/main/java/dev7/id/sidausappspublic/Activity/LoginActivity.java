package dev7.id.sidausappspublic.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.grisoftware.updatechecker.GoogleChecker;

import dev7.id.sidausappspublic.Model.User;
import dev7.id.sidausappspublic.R;
import dev7.id.sidausappspublic.Server.ApiUtil;
import dev7.id.sidausappspublic.Server.UserInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private UserInterface userInterface = ApiUtil.getUserInterface();
    private SharedPreferences setting;
    private static final String TAG = "Login";
    private TextInputEditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        doLogin();
        doRegis();
    }


    private void initView() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegis = findViewById(R.id.tvRegis);
        setting = getSharedPreferences("USER", MODE_PRIVATE);
    }

    private void doRegis() {
        tvRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(a);
            }
        });
    }

    private void doLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (!username.isEmpty() && !password.isEmpty()) {
                    if (username.length() < 2) {
                        etUsername.setError("Masukan Username dengan benar");
                        etUsername.requestFocus();
                        return;
                    }
                    if (password.length() < 4) {
                        etPassword.setError("Masukan Sandi dengan benar");
                        etPassword.requestFocus();
                        return;
                    }
                    btnLogin.setEnabled(false);
                    Call<User> user = userInterface.login(username, password);
                    user.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                                User result = response.body();
                                if (result != null) {
                                    Log.i("done", result.toString());
                                    System.out.println("kie "+result.getToken());
                                    setLoggedIn(result.getToken());
                                    Intent a= new Intent(LoginActivity.this, TestActivity.class);
                                    new GoogleChecker("dev7.id.sidausappspublic",LoginActivity.this, false,"en");

                                    startActivity(a);
//                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, response +"Gagal Login", Toast.LENGTH_SHORT).show();
                                    System.err.println("apaaan " + response);
                                }
                            btnLogin.setEnabled(true);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            btnLogin.setEnabled(true);
                            System.err.println("ydhn" + t.getMessage());
                            Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
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
