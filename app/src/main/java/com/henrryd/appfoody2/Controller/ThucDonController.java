package com.henrryd.appfoody2.Controller;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.henrryd.appfoody2.Adapters.AdapterThucDon;
import com.henrryd.appfoody2.Controller.Interfaces.ThucDonInterface;
import com.henrryd.appfoody2.Model.ThucDonModel;

import java.util.List;


public class ThucDonController {
    ThucDonModel thucDonModel;

    public ThucDonController(){
        thucDonModel = new ThucDonModel();
    }

    public void getDanhSachThucDonQuanAnTheoMa(final Context context, String manquanan, final RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ThucDonInterface thucDonInterface = new ThucDonInterface() {
            @Override
            public void getThucDonThanhCong(List<ThucDonModel> thucDonModelList) {
                AdapterThucDon adapterThucDon = new AdapterThucDon(context,thucDonModelList);
                recyclerView.setAdapter(adapterThucDon);
                adapterThucDon.notifyDataSetChanged();
            }
        };
        thucDonModel.getDanhSachThucDonQuanAnTheoMa(manquanan,thucDonInterface);
    }
}
