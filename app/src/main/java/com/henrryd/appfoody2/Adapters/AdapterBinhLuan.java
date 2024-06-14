package com.henrryd.appfoody2.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.henrryd.appfoody2.Model.BinhLuanModel;
import com.henrryd.appfoody2.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterBinhLuan extends RecyclerView.Adapter<AdapterBinhLuan.ViewHolder> {

    Context context;
    int Layout;
    List<BinhLuanModel> binhLuanModelList;
    List<Bitmap> bitmapList;
    public AdapterBinhLuan(Context context, int Layout, List<BinhLuanModel> binhLuanModelList){

        this.context = context;
        this.Layout = Layout;
        bitmapList = new ArrayList<>();
        this.binhLuanModelList = binhLuanModelList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView cicleImageUser;
        TextView txtTieudebinhluan,txtNodungbinhluan,txtChamDiemBinhLuan;
        RecyclerView recyclerViewHinhBinhLuan;
        public ViewHolder(View itemView) {
            super(itemView);
            cicleImageUser = itemView.findViewById(R.id.cicleImageUser);
            txtTieudebinhluan = itemView.findViewById(R.id.txtTieudebinhluan);
            txtNodungbinhluan = itemView.findViewById(R.id.txtNodungbinhluan);
            txtChamDiemBinhLuan = itemView.findViewById(R.id.txtChamDiemBinhLuan);
            recyclerViewHinhBinhLuan = itemView.findViewById(R.id.recyclerHinhBinhLuan);

        }
    }

    @Override
    public AdapterBinhLuan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(Layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterBinhLuan.ViewHolder holder, int position) {
        final BinhLuanModel binhLuanModel = binhLuanModelList.get(position);
        holder.txtTieudebinhluan.setText(binhLuanModel.getTieude());
        holder.txtNodungbinhluan.setText(binhLuanModel.getNoidung());
        holder.txtChamDiemBinhLuan.setText(binhLuanModel.getChamdiem() + "");
        if (binhLuanModel.getThanhVienModel() != null) {
            loadFirebaseImage(holder.cicleImageUser, "thanhvien/" + binhLuanModel.getThanhVienModel().getHinhanh());
        }


        for (String linkhinh : binhLuanModel.getHinhanhBinhLuanList()){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh").child("listhinh");
            long ONE_MEGABYTE = 1024 * 1024;
            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);
                    if (bitmapList.size() == binhLuanModel.getHinhanhBinhLuanList().size()){
                        AdapterRecyclerHinhBinhLuan adapterRecyclerHinhBinhLuan = new AdapterRecyclerHinhBinhLuan(context, R.layout.custom_layout_hinhbinhluan, bitmapList);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,2);
                        holder.recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                        holder.recyclerViewHinhBinhLuan.setAdapter(adapterRecyclerHinhBinhLuan);
                        adapterRecyclerHinhBinhLuan.notifyDataSetChanged();
                    }


                }
            });
        }

    }

    @Override
    public int getItemCount() {
        int soBinhLuan = binhLuanModelList.size();
        if(soBinhLuan > 5){
            return 5;
        }else{
            return binhLuanModelList.size();
        }
    }
    private void loadFirebaseImage(ImageView imageView, String path) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(path);
        long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("AdapterRecyclerOdau", "Error loading image", exception);
            }
        });
    }


}
