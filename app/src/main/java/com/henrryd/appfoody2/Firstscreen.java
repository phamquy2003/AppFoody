package com.henrryd.appfoody2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Firstscreen extends AppCompatActivity {

    Button btnOrder;
    ProgressBar progressBar;
    TextView txtPhienBan;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_firstscreen);

        btnOrder = findViewById(R.id.btnOrder);
        progressBar = findViewById(R.id.progressBar);
        txtPhienBan = findViewById(R.id.txtPhienBan);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataEntered();
            }
        });

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
            txtPhienBan.setText(getString(R.string.phienban) + " " + packageInfo.versionName);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent Login = new Intent(Firstscreen.this, LoginActivity.class);
                    startActivity(Login);
                }
            },2000);

        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
    }
    void checkDataEntered() {

    }

}