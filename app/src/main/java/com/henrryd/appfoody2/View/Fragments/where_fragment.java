package com.henrryd.appfoody2.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.henrryd.appfoody2.Controller.OdauController;
import com.henrryd.appfoody2.Model.QuanAnModel;
import com.henrryd.appfoody2.R;

public class where_fragment extends Fragment {
    OdauController odauController;
    RecyclerView recyclerOdau;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_odau,container,false);
        recyclerOdau = view.findViewById(R.id.recyclerOdau);
        odauController = new OdauController(getContext());
        odauController.getDanhSachQuanAnController(recyclerOdau);
        return view;
    }

    public void onStart() {

        super.onStart();

    }
}
