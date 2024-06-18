package com.henrryd.appfoody2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.henrryd.appfoody2.Model.DatMon;
import com.henrryd.appfoody2.Model.MonAnModel;
import com.henrryd.appfoody2.R;

import java.util.ArrayList;
import java.util.List;


public class AdapterMonAn extends RecyclerView.Adapter<AdapterMonAn.HolderMonAn> {

    Context context;
    List<MonAnModel> monAnModelList;
    public static List<DatMon> datMonList = new ArrayList<>();

    public AdapterMonAn(Context context, List<MonAnModel> monAnModelList){
        this.context = context;
        this.monAnModelList = monAnModelList;

    }

    @Override
    public HolderMonAn onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_monan,parent,false);
        return new HolderMonAn(view);
    }

    @Override
    public void onBindViewHolder(final HolderMonAn holder, int position) {
        final MonAnModel monAnModel = monAnModelList.get(position);
        holder.txtTenMonAn.setText(monAnModel.getTenmon());

        holder.txtSoLuong.setTag(0);
        holder.imgTangSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = Integer.parseInt(holder.txtSoLuong.getTag().toString());
                dem++;
                holder.txtSoLuong.setText(dem+"");
                holder.txtSoLuong.setTag(dem);

                DatMon datMonTag = (DatMon) holder.imgGiamSoLuong.getTag();
                if(datMonTag != null){
                    AdapterMonAn.datMonList.remove(datMonTag);
                }

                DatMon datMon = new DatMon();
                datMon.setSoLuong(dem);
                datMon.setTenMonAn(monAnModel.getTenmon());

                holder.imgGiamSoLuong.setTag(datMon);

                AdapterMonAn.datMonList.add(datMon);

            }
        });

        holder.imgGiamSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = Integer.parseInt(holder.txtSoLuong.getTag().toString());
                if(dem != 0){
                    dem--;
                    if(dem == 0){
                        DatMon datMon = (DatMon) v.getTag();
                        AdapterMonAn.datMonList.remove(datMon);
                    }
                }

                holder.txtSoLuong.setText(dem+"");
                holder.txtSoLuong.setTag(dem);

            }
        });
    }

    @Override
    public int getItemCount() {
        return monAnModelList.size();
    }

    public class HolderMonAn extends RecyclerView.ViewHolder {
        TextView txtTenMonAn,txtSoLuong;
        ImageView imgGiamSoLuong,imgTangSoLuong;

        public HolderMonAn(View itemView) {
            super(itemView);
            txtTenMonAn = (TextView) itemView.findViewById(R.id.txtTenMonAn);
            txtSoLuong = (TextView) itemView.findViewById(R.id.txtSoLuong);
            imgGiamSoLuong = (ImageView) itemView.findViewById(R.id.imgGiamSoLuong);
            imgTangSoLuong = (ImageView) itemView.findViewById(R.id.imgTangSoLuong);
        }
    }
}
