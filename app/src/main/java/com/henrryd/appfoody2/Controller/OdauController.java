package com.henrryd.appfoody2.Controller;

import android.content.Context;
import android.location.Location;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.henrryd.appfoody2.Adapters.AdapterRecyclerOdau;
import com.henrryd.appfoody2.Controller.Interfaces.OdauInterface;
import com.henrryd.appfoody2.Model.QuanAnModel;
import com.henrryd.appfoody2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class  OdauController {
    Context context;
    QuanAnModel quanAnModel;

    public OdauController(Context context) {
        this.context = context;
        this.quanAnModel = new QuanAnModel();
    }

    public void getDanhSachQuanAnController(Context context, final RecyclerView recyclerOdau, final ProgressBar progressBar, Location vitrihientai) {
        final List<QuanAnModel> quanAnModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerOdau.setLayoutManager(layoutManager);
        final AdapterRecyclerOdau adapterRecyclerOdau = new AdapterRecyclerOdau(context, quanAnModelList, R.layout.layout_recyclerview_odau);
        recyclerOdau.setAdapter(adapterRecyclerOdau);

        OdauInterface odauInterface = new OdauInterface() {
            @Override
            public void getDanhSachQuanAnModel(QuanAnModel quanAnModel) {
                quanAnModelList.add(quanAnModel);
                adapterRecyclerOdau.notifyDataSetChanged(); // Notify the adapter that data has changed
                progressBar.setVisibility(View.GONE);
            }
        };

        quanAnModel.getDanhSachQuanAn(odauInterface, vitrihientai);

        progressBar.setVisibility(View.VISIBLE);
    }
}
