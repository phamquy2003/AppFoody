package com.henrryd.appfoody2.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.henrryd.appfoody2.Model.ThucDonModel;
import com.henrryd.appfoody2.R;

import java.util.List;


public class AdapterThucDon extends RecyclerView.Adapter<AdapterThucDon.HolderThucDon> {

    Context context;
    List<ThucDonModel> thucDonModels;

    public AdapterThucDon(Context context, List<ThucDonModel> thucDonModels){
        this.context = context;
        this.thucDonModels = thucDonModels;
    }

    @Override
    public HolderThucDon onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_thucdon,parent,false);
        return new HolderThucDon(view);
    }

    @Override
    public void onBindViewHolder(HolderThucDon holder, int position) {
        ThucDonModel thucDonModel = thucDonModels.get(position);
        holder.txtThucDon.setText(thucDonModel.getTenthucdon());
        holder.recyclerViewMonAn.setLayoutManager(new LinearLayoutManager(context));
        AdapterMonAn adapterMonAn = new AdapterMonAn(context,thucDonModel.getMonAnModelList());
        holder.recyclerViewMonAn.setAdapter(adapterMonAn);
        adapterMonAn.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return thucDonModels.size();
    }

    public class HolderThucDon extends RecyclerView.ViewHolder {
        TextView txtThucDon;
        RecyclerView recyclerViewMonAn;
        public HolderThucDon(View itemView) {
            super(itemView);

            txtThucDon = (TextView) itemView.findViewById(R.id.txtTenThucDon);
            recyclerViewMonAn = (RecyclerView) itemView.findViewById(R.id.recyclerMonAn);
        }
    }
}
