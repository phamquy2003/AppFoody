package com.henrryd.appfoody2.Adapters;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.henrryd.appfoody2.Model.QuanAnModel;
import com.henrryd.appfoody2.R;

import java.util.List;

public class AdapterRecyclerOdau extends RecyclerView.Adapter< AdapterRecyclerOdau.ViewHolder> {
    List<QuanAnModel> quanAnModelList;
    int resource;
    public AdapterRecyclerOdau(List<QuanAnModel> quanAnModelList, int resource){
        this.quanAnModelList = quanAnModelList;
        this.resource = resource;
    }

    @NonNull
    @Override
    public AdapterRecyclerOdau.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerOdau.ViewHolder holder, int position) {
        QuanAnModel quanAnModel = quanAnModelList.get(position);
        holder.txtTenQuanAnOdau.setText(quanAnModel.getTenquanan());
        if (quanAnModel.isGiaohang()) {
            holder.btnOderOdau.setVisibility(View.VISIBLE);
        }
        if (quanAnModel.getHinhanhquanan() != null && !quanAnModel.getHinhanhquanan().isEmpty()) {
            String hinhAnh = quanAnModel.getHinhanhquanan().get(0);
            StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanh").child(hinhAnh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.imageHinhQuanAnOdau.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("AdapterRecyclerOdau", "Error loading image", exception);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return quanAnModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenQuanAnOdau;
        Button btnOderOdau;
        ImageView imageHinhQuanAnOdau;
         public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenQuanAnOdau = itemView.findViewById(R.id.txtTenQuanAnOdau);
            btnOderOdau = itemView.findViewById(R.id.btnOderOdau);
            imageHinhQuanAnOdau = itemView.findViewById(R.id.imageHinhQuanAnOdau);
        }
    }
}
