package com.henrryd.appfoody2.View.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.henrryd.appfoody2.Adapters.AdapterRecyclerHinhBinhLuan;
import com.henrryd.appfoody2.Model.BinhLuanModel;
import com.henrryd.appfoody2.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HienThiChiTietBinhLuanActivity extends AppCompatActivity {

    CircleImageView circleImageView;
    TextView txtTieuDeBinhLuan, txtNoiDungBinhLuan, txtSoDiem;
    RecyclerView recyclerViewHinhBinhLuan;
    List<Bitmap> bitmapList;
    BinhLuanModel binhLuanModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_binhluan);

        circleImageView = findViewById(R.id.cicleImageUser);
        txtTieuDeBinhLuan = findViewById(R.id.txtTieudebinhluan);
        txtNoiDungBinhLuan = findViewById(R.id.txtNodungbinhluan);
        txtSoDiem = findViewById(R.id.txtChamDiemBinhLuan);
        recyclerViewHinhBinhLuan = findViewById(R.id.recyclerHinhBinhLuan);

        bitmapList = new ArrayList<>();

        // Nhận dữ liệu từ Intent
        binhLuanModel = getIntent().getParcelableExtra("binhluanmodel");

        // Kiểm tra binhLuanModel có null không trước khi truy cập
        if (binhLuanModel != null) {
            txtTieuDeBinhLuan.setText(binhLuanModel.getTieude());
            txtNoiDungBinhLuan.setText(binhLuanModel.getNoidung());
            txtSoDiem.setText(String.valueOf(binhLuanModel.getChamdiem()));
            setHinhAnhBinhLuan(circleImageView, binhLuanModel.getThanhVienModel().getHinhanh());

            for (String linkhinh : binhLuanModel.getHinhanhBinhLuanList()) {
                StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("hinhanh/").child(linkhinh);
                long ONE_MEGABYTE = 1024 * 1024;
                storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        bitmapList.add(bitmap);
                        if (bitmapList.size() == binhLuanModel.getHinhanhBinhLuanList().size()) {
                            AdapterRecyclerHinhBinhLuan adapterRecyclerHinhBinhLuan = new AdapterRecyclerHinhBinhLuan(HienThiChiTietBinhLuanActivity.this, R.layout.custom_layout_hinhbinhluan, bitmapList, binhLuanModel, true);
                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(HienThiChiTietBinhLuanActivity.this, 2);
                            recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                            recyclerViewHinhBinhLuan.setAdapter(adapterRecyclerHinhBinhLuan);
                            adapterRecyclerHinhBinhLuan.notifyDataSetChanged();
                        }
                    }
                });
            }
        } else {
            // Xử lý khi binhLuanModel là null (ví dụ: hiển thị thông báo lỗi hoặc quay lại activity trước)
            // Hoặc bạn có thể log lỗi để debug
            Log.e("HienThiChiTietBinhLuan", "binhLuanModel is null");
            finish(); // Đóng activity hiện tại
        }
    }

    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh) {
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }
}