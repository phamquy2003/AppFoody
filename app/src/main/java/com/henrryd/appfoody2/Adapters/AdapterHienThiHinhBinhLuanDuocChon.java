package com.henrryd.appfoody2.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.henrryd.appfoody2.R;

import java.util.List;

public class AdapterHienThiHinhBinhLuanDuocChon extends RecyclerView.Adapter<AdapterHienThiHinhBinhLuanDuocChon.ViewHolderHinhBinhLuan> {

    Context context;
    int resource;
    List<String> list;

    public AdapterHienThiHinhBinhLuanDuocChon(Context context, int resource, List<String> list){
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public ViewHolderHinhBinhLuan onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolderHinhBinhLuan viewHolderHinhBinhLuan = new ViewHolderHinhBinhLuan(view);
        return viewHolderHinhBinhLuan;
    }

    @Override
    public void onBindViewHolder(ViewHolderHinhBinhLuan holder, int position) {
        String uriString = list.get(position);
        Uri uri = Uri.parse(uriString);
        try {
            holder.imageView.setImageURI(uri);
        } catch (Exception e) {
            Toast.makeText(context, "Error loading image: " + uriString, Toast.LENGTH_SHORT).show();
        }

        holder.imgXoa.setTag(position);
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vitri = (int) v.getTag();
                list.remove(vitri);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderHinhBinhLuan extends RecyclerView.ViewHolder {
        ImageView imageView, imgXoa;

        public ViewHolderHinhBinhLuan(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgChonHinhBinhLuan);
            imgXoa = itemView.findViewById(R.id.imgeDelete);
        }
    }
}
