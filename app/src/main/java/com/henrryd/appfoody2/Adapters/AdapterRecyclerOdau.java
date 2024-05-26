package com.henrryd.appfoody2.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.henrryd.appfoody2.Model.BinhLuanModel;
import com.henrryd.appfoody2.Model.QuanAnModel;
import com.henrryd.appfoody2.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRecyclerOdau extends RecyclerView.Adapter<AdapterRecyclerOdau.ViewHolder> {
    List<QuanAnModel> quanAnModelList;
    int resource;

    public AdapterRecyclerOdau(List<QuanAnModel> quanAnModelList, int resource) {
        this.quanAnModelList = quanAnModelList;
        this.resource = resource;
    }

    @NonNull
    @Override
    public AdapterRecyclerOdau.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerOdau.ViewHolder holder, int position) {
        QuanAnModel quanAnModel = quanAnModelList.get(position);
        holder.txtTenQuanAnOdau.setText(quanAnModel.getTenquanan());

        if (quanAnModel.isGiaohang()) {
            holder.btnOderOdau.setVisibility(View.VISIBLE);
        } else {
            holder.btnOderOdau.setVisibility(View.GONE);
        }

        if (quanAnModel.getHinhanhquanan() != null && !quanAnModel.getHinhanhquanan().isEmpty()) {
            String hinhAnh = quanAnModel.getHinhanhquanan().get(0);
            loadFirebaseImage(holder.imageHinhQuanAnOdau, "hinhanh/" + hinhAnh);
        }

        if (quanAnModel.getBinhLuanModelList() != null && !quanAnModel.getBinhLuanModelList().isEmpty()) {
            BinhLuanModel binhLuanModel = quanAnModel.getBinhLuanModelList().get(0);
            holder.txtTitleComment.setText(binhLuanModel.getTieude());
            holder.txtContentComment.setText(binhLuanModel.getNoidung());
            holder.txtPoint.setText(binhLuanModel.getChamdiem() + "");
            if (binhLuanModel.getThanhVienModel() != null) {
                loadFirebaseImage(holder.circleImageUser, "thanhvien/" + binhLuanModel.getThanhVienModel().getHinhanh());
            }

            if (quanAnModel.getBinhLuanModelList().size() > 1) {
                BinhLuanModel binhLuanModel2 = quanAnModel.getBinhLuanModelList().get(1);
                holder.txtTitleComment2.setText(binhLuanModel2.getTieude());
                holder.txtContentComment2.setText(binhLuanModel2.getNoidung());
                holder.txtPoint2.setText(binhLuanModel.getChamdiem() + "");
                if (binhLuanModel2.getThanhVienModel() != null) {
                    loadFirebaseImage(holder.circleImageUser2, "thanhvien/" + binhLuanModel2.getThanhVienModel().getHinhanh());
                }
            }
        }else {
            holder.containerComment.setVisibility(View.GONE);
            holder.containerComment2.setVisibility(View.GONE);
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

    @Override
    public int getItemCount() {
        return quanAnModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenQuanAnOdau, txtTitleComment, txtTitleComment2, txtContentComment, txtContentComment2, txtPoint, txtPoint2;
        Button btnOderOdau;
        ImageView imageHinhQuanAnOdau;
        CircleImageView circleImageUser, circleImageUser2;
        LinearLayout containerComment, containerComment2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenQuanAnOdau = itemView.findViewById(R.id.txtTenQuanAnOdau);
            btnOderOdau = itemView.findViewById(R.id.btnOderOdau);
            imageHinhQuanAnOdau = itemView.findViewById(R.id.imageHinhQuanAnOdau);
            txtTitleComment = itemView.findViewById(R.id.txtTitleComment);
            txtTitleComment2 = itemView.findViewById(R.id.txtTitleComment2);
            txtContentComment = itemView.findViewById(R.id.txtContentComment);
            txtContentComment2 = itemView.findViewById(R.id.txtContentComment2);
            circleImageUser = itemView.findViewById(R.id.circleImageUser);
            circleImageUser2 = itemView.findViewById(R.id.circleImageUser2);
            containerComment = itemView.findViewById(R.id.containerComment);
            containerComment2 = itemView.findViewById(R.id.containerComment2);
            txtPoint = itemView.findViewById(R.id.txtPoint);
            txtPoint2 = itemView.findViewById(R.id.txtPoint2);
        }
    }
}
