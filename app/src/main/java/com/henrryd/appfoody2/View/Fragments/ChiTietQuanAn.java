package com.henrryd.appfoody2.View.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.henrryd.appfoody2.Adapters.AdapterBinhLuan;
import com.henrryd.appfoody2.Model.QuanAnModel;
import com.henrryd.appfoody2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChiTietQuanAn extends AppCompatActivity {

    TextView txtTieuDeToolbar, txtTenQuanAn, txtDiaChiQuanAn, txtThoiGianHoatDong, txtStatus, tongSoHinhAnh, tongSoBinhLuan, tongSoCheckIn, tongSoLuuLai;
    ImageView imgHinhQuanAn;
    QuanAnModel quanAnModel;
    Toolbar toolbar;
    RecyclerView txtRecyclerChiTietQuanAn;
    AdapterBinhLuan adapterBinhLuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_quan_an);

        // Initialize views
        txtTenQuanAn = findViewById(R.id.txtTenQuanAn);
        txtDiaChiQuanAn = findViewById(R.id.txtDiaChiQuanAn);
        txtThoiGianHoatDong = findViewById(R.id.txtThoiGianHoatDong);
        txtStatus = findViewById(R.id.txtStatus);
        tongSoHinhAnh = findViewById(R.id.tongSoHinhAnh);
        tongSoBinhLuan = findViewById(R.id.tongSoBinhLuan);
        tongSoCheckIn = findViewById(R.id.tongSoCheckIn);
        tongSoLuuLai = findViewById(R.id.tongSoLuuLai);
        imgHinhQuanAn = findViewById(R.id.imgHinhQuanAn);
        txtTieuDeToolbar = findViewById(R.id.txtTieuDeToolbar);
        toolbar = findViewById(R.id.toolbar); // Correct import to androidx.appcompat.widget.Toolbar
        txtRecyclerChiTietQuanAn = findViewById(R.id.txtRecyclerChiTietQuanAn);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get data from intent
        quanAnModel = getIntent().getParcelableExtra("quanan");

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String nowTime = dateFormat.format(calendar.getTime());
        Log.d("giohientai", nowTime + "");
        String openTime = quanAnModel.getGiomocua();
        String closeTime = quanAnModel.getGiodongcua();

        try {

            Date nowDate = dateFormat.parse(nowTime);
            Date openDate = dateFormat.parse(openTime);
            Date closeDate = dateFormat.parse(closeTime);

            if(nowDate.after(openDate) && nowDate.before(closeDate)){
                txtThoiGianHoatDong.setText(getString(R.string.dangmocua));
            }else{
                txtThoiGianHoatDong.setText(getString(R.string.dadongcua));
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        txtTieuDeToolbar.setText(quanAnModel.getTenquanan());

        txtTenQuanAn.setText(quanAnModel.getTenquanan());
        txtDiaChiQuanAn.setText(quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
        txtThoiGianHoatDong.setText(quanAnModel.getGiomocua() + " - " + quanAnModel.getGiodongcua());
        tongSoHinhAnh.setText(String.valueOf(quanAnModel.getHinhanhquanan().size()));
        tongSoBinhLuan.setText(String.valueOf(quanAnModel.getBinhLuanModelList().size()));
        txtThoiGianHoatDong.setText(openTime + " - " + closeTime);

        StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAnModel.getHinhanhquanan().get(0));
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imgHinhQuanAn.setImageBitmap(bitmap);
            }
        });

        //Load danh sach binh luan cua quan
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        txtRecyclerChiTietQuanAn.setLayoutManager(layoutManager);
        adapterBinhLuan = new AdapterBinhLuan(this,R.layout.custom_layout_binhluan,quanAnModel.getBinhLuanModelList());
        txtRecyclerChiTietQuanAn.setAdapter(adapterBinhLuan);
        adapterBinhLuan.notifyDataSetChanged();

        NestedScrollView nestedScrollViewChiTiet = (NestedScrollView) findViewById(R.id.nestedScollViewChiTiet);
        nestedScrollViewChiTiet.smoothScrollTo(0,0);
    }





}
