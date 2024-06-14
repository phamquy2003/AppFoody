package com.henrryd.appfoody2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class Firstscreen extends AppCompatActivity {

    ProgressBar progressBar;
    TextView txtPhienBan;
    private static final int REQUEST_PERMISSION_LOCATION = 1;
    SharedPreferences sharedPreferences;
    FusedLocationProviderClient fusedLocationClient;
    LocationCallback locationCallback;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstscreen);

        progressBar = findViewById(R.id.progressBar);
        txtPhienBan = findViewById(R.id.txtPhienBan);
        sharedPreferences = getSharedPreferences("toado", MODE_PRIVATE);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
        } else {
            getLastLocation();
        }

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.d("Location", "Location callback result is null");
                    return;
                }
                for (Location location : locationResult.getLocations()) {
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

                        updateUI();
                        break;
                    }
                }
            }
        };
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

                            updateUI();
                        } else {
                            Log.d("Location", "Current location is null");
                            requestNewLocationData();
                        }
                    }
                });
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setNumUpdates(1);

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void updateUI() {
        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            txtPhienBan.setText(getString(R.string.phienban) + " " + versionName);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent loginIntent = new Intent(Firstscreen.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }, 2000);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Gọi lớp cha để xử lý
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Log.e("Permission", "Permission denied.");
                Toast.makeText(this, "Permission denied. App cannot function without location access.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
