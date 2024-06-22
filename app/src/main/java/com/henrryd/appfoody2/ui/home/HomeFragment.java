package com.henrryd.appfoody2.ui.home;

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

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.henrryd.appfoody2.Controller.OdauController;
import com.henrryd.appfoody2.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    OdauController odauController;
    RecyclerView recyclerOdau;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerOdau = view.findViewById(R.id.recyclerOdau);
        odauController = new OdauController(getContext());
        progressBar = view.findViewById(R.id.progressBar);

        ImageSlider imgSlider = view.findViewById(R.id.imageSlider);

        ArrayList<SlideModel> lstquangcao = new ArrayList<>();
        lstquangcao.add(new SlideModel(R.drawable.bannerqc1, null));
        lstquangcao.add(new SlideModel(R.drawable.bannerpc2, null));
        lstquangcao.add(new SlideModel(R.drawable.bannerpc3, null));
        lstquangcao.add(new SlideModel(R.drawable.bannerqc4, null));

        imgSlider.setImageList(lstquangcao, ScaleTypes.CENTER_CROP);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);

        String latitudeString = sharedPreferences.getString("latitude", "0");
        String longitudeString = sharedPreferences.getString("longitude", "0");

        try {
            double latitude = Double.parseDouble(latitudeString);
            double longitude = Double.parseDouble(longitudeString);

            Location vitrihientai = new Location("");
            vitrihientai.setLatitude(latitude);
            vitrihientai.setLongitude(longitude);

            Log.d("kiemtraodau", "Latitude: " + latitude + ", Longitude: " + longitude);

            odauController = new OdauController(getContext());
            odauController.getDanhSachQuanAnController(getContext(), recyclerOdau, progressBar, vitrihientai);
        } catch (NumberFormatException e) {
            Log.e("kiemtraodau", "Invalid latitude or longitude format", e);
        }
    }
}
