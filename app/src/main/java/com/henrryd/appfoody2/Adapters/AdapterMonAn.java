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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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

    @NonNull
    @Override
    public HolderMonAn onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_monan, parent, false);
        return new HolderMonAn(view);
    }

    public String prepareOrderSummary() {
        StringBuilder summary = new StringBuilder();
        for (DatMon datMon : datMonList) {
            summary.append("Tên món: ").append(datMon.getTenMonAn())
                    .append(", Số lượng: ").append(datMon.getSoLuong())
                    .append("\n");
        }
        return summary.toString();
    }
    @Override
    public void onBindViewHolder(@NonNull final HolderMonAn holder, int position) {
        final MonAnModel monAnModel = monAnModelList.get(position);
        holder.txtTenMonAn.setText(monAnModel.getTenmon());
        holder.txtGiaTien.setText(String.valueOf(monAnModel.getGiatien()));

        // Kiểm tra và tải hình ảnh từ Firebase Storage
        if (monAnModel.getHinhanh() != null && !monAnModel.getHinhanh().isEmpty()) {
            String imageName = monAnModel.getHinhanh(); // Lấy tên hình ảnh từ model của bạn
            loadFirebaseImage(holder.imgHinhMonAn, "hinhanh/" + imageName);
        } else {
            holder.imgHinhMonAn.setImageResource(R.mipmap.image_avt); // Nếu không có hình ảnh, sử dụng ảnh mặc định
        }

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

                Log.d("AdapterMonAn", "Danh sách món đã đặt:\n" + prepareOrderSummary());
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

                Log.d("AdapterMonAn", "Danh sách món đã đặt:\n" + prepareOrderSummary());

            }
        });

    }

    @Override
    public int getItemCount() {
        return monAnModelList.size();
    }

    private void loadFirebaseImage(ImageView imageView, String path) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(path);
        long ONE_MEGABYTE = 1024 * 1024; // Kích thước tối đa cho phép tải xuống là 1 MB
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Chuyển đổi mảng byte thành Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                // Đặt Bitmap vào ImageView
                imageView.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Xử lý khi tải hình ảnh thất bại
                Log.e("AdapterMonAn", "Error loading image", exception);
                imageView.setImageResource(R.mipmap.image_avt); // Đặt ảnh mặc định nếu tải thất bại
            }
        });

    }

    public class HolderMonAn extends RecyclerView.ViewHolder {
        TextView txtTenMonAn, txtSoLuong, txtGiaTien;
        ImageView imgGiamSoLuong, imgTangSoLuong, imgHinhMonAn;

        public HolderMonAn(View itemView) {
            super(itemView);
            txtTenMonAn = itemView.findViewById(R.id.txtTenMonAn);
            txtGiaTien = itemView.findViewById(R.id.txtGiaTien);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            imgGiamSoLuong = itemView.findViewById(R.id.imgGiamSoLuong);
            imgTangSoLuong = itemView.findViewById(R.id.imgTangSoLuong);
            imgHinhMonAn = itemView.findViewById(R.id.imgHinhMonAn);
        }
    }
}
