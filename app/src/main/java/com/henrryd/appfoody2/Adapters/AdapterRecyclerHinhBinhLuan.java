package com.henrryd.appfoody2.Adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.henrryd.appfoody2.Model.BinhLuanModel;
import com.henrryd.appfoody2.R;
import com.henrryd.appfoody2.View.Fragments.HienThiChiTietBinhLuanActivity;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerHinhBinhLuan extends RecyclerView.Adapter<AdapterRecyclerHinhBinhLuan.ViewHolderHinhBinhLuan> {
    Context context;
    int resource;
    BinhLuanModel binhLuanModel;
    List<Bitmap> bitmapList;
    List<Bitmap> listHinh;
    Boolean isDetailComment;

    public AdapterRecyclerHinhBinhLuan(Context context, int layout, List<Bitmap> listHinh, BinhLuanModel binhLuanModel, Boolean isDetailComment) {
        this.context = context;
        this.resource = layout; // Sửa chỗ này
        this.listHinh = listHinh;
        this.binhLuanModel = binhLuanModel;
        bitmapList = new ArrayList<>();
        this.isDetailComment = isDetailComment;

    }

    public class ViewHolderHinhBinhLuan extends RecyclerView.ViewHolder {
        ImageView imageHinhBinhLuon;
        TextView txtSoHinhBinhLuan;
        FrameLayout khungSoHinhBinhLuan;

        public ViewHolderHinhBinhLuan(View itemView) {
            super(itemView);
            imageHinhBinhLuon = itemView.findViewById(R.id.imageBinhLuan);
            txtSoHinhBinhLuan =  itemView.findViewById(R.id.txtSoHinhBinhLuan);
            khungSoHinhBinhLuan =  itemView.findViewById(R.id.khungSoHinhBinhLuan);
        }
    }

    @Override
    public AdapterRecyclerHinhBinhLuan.ViewHolderHinhBinhLuan onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolderHinhBinhLuan(view); // Sửa chỗ này
    }

    @Override
    public void onBindViewHolder(final AdapterRecyclerHinhBinhLuan.ViewHolderHinhBinhLuan holder, final int position) {

        holder.imageHinhBinhLuon.setImageBitmap(listHinh.get(position));

        if (!isDetailComment) {
            if (position == 3) {

                int sohinhconlai = listHinh.size() - 4;
                if (sohinhconlai > 0) {
                    holder.khungSoHinhBinhLuan.setVisibility(View.VISIBLE);
                    holder.txtSoHinhBinhLuan.setText("+" + sohinhconlai);
                    holder.imageHinhBinhLuon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("batsukien", "onClick");
                            Intent iChiTietBinhLuan = new Intent(context, HienThiChiTietBinhLuanActivity.class);
                            iChiTietBinhLuan.putExtra("binhluanmodel", binhLuanModel);
                            context.startActivity(iChiTietBinhLuan);
                        }
                    });

                }

            }
        }
    }
    @Override
    public int getItemCount() {
        if(!isDetailComment){
            if(listHinh.size() < 4){
                return listHinh.size();
            }else{
                return 4;
            }

        }else{
            return listHinh.size();
        }


    }
}
