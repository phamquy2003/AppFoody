package com.henrryd.appfoody2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class Firstscreen extends AppCompatActivity {

    ProgressBar progressBar;
    TextView txtPhienBan;
    private static final int REQUEST_PERMISSION_LOCATION = 1;
    SharedPreferences sharedPreferences;
    FusedLocationProviderClient fusedLocationClient;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_firstscreen);

        progressBar = findViewById(R.id.progressBar);
        txtPhienBan = findViewById(R.id.txtPhienBan);
        sharedPreferences = getSharedPreferences("toado", MODE_PRIVATE);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        int checkPermissionCoarseLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (checkPermissionCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
        } else {
            getLastLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("latitude", String.valueOf(location.getLatitude()));
                            editor.putString("longitude", String.valueOf(location.getLongitude()));
                            editor.apply();
                            Log.d("Location", "Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());

                            // Verify the saved values
                            String savedLatitude = sharedPreferences.getString("latitude", "0");
                            String savedLongitude = sharedPreferences.getString("longitude", "0");
                            Log.d("SavedLocation", "Saved Latitude: " + savedLatitude + " Saved Longitude: " + savedLongitude);
                        } else {
                            Log.d("Location", "Current location is null");
                        }
                        try {
                            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                            txtPhienBan.setText(getString(R.string.phienban) + " " + packageInfo.versionName);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent Login = new Intent(Firstscreen.this, LoginActivity.class);
                                    startActivity(Login);
                                }
                            }, 2000);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                }
                break;
        }
    }
}
