package dev7.id.sidausappspublic.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.grisoftware.updatechecker.GoogleChecker;

import dev7.id.sidausappspublic.R;

public class Splash extends AppCompatActivity {
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        image = findViewById(R.id.image);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home=new Intent(Splash.this, RegisterActivity.class);
                new GoogleChecker("dev7.id.sidausappspublic",Splash.this, false,"en");
                startActivity(home);
                finish();
            }
        },3000);


    }
}
