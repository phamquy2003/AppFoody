package com.henrryd.appfoody2.View.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.henrryd.appfoody2.Controller.OdauController;
import com.henrryd.appfoody2.R;

public class where_fragment extends Fragment {
    OdauController odauController;
    RecyclerView recyclerOdau;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_odau, container, false);
        recyclerOdau = view.findViewById(R.id.recyclerOdau);
        odauController = new OdauController(getContext());
        progressBar = view.findViewById(R.id.progressBar);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);

        // Lấy tọa độ từ SharedPreferences và ép kiểu sang double
        String latitudeString = sharedPreferences.getString("latitude", "0");
        String longitudeString = sharedPreferences.getString("longitude", "0");

        try {
            double latitude = Double.parseDouble(latitudeString);
            double longitude = Double.parseDouble(longitudeString);

            // Tạo một đối tượng Location và thiết lập tọa độ
            Location vitrihientai = new Location("");
            vitrihientai.setLatitude(latitude);
            vitrihientai.setLongitude(longitude);

            Log.d("kiemtraodau", "Latitude: " + latitude + ", Longitude: " + longitude);

            odauController = new OdauController(getContext());
            odauController.getDanhSachQuanAnController(recyclerOdau, progressBar, vitrihientai);
        } catch (NumberFormatException e) {
            Log.e("kiemtraodau", "Invalid latitude or longitude format", e);
        }

    }
}
