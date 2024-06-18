package com.henrryd.appfoody2.View.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.henrryd.appfoody2.Adapters.AdapterHienThiHinhBinhLuanDuocChon;
import com.henrryd.appfoody2.Controller.BinhLuanController;
import com.henrryd.appfoody2.Model.BinhLuanModel;
import com.henrryd.appfoody2.R;

import java.util.ArrayList;
import java.util.List;

public class BinhLuanActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtTenQuanAn, txtDiaChiQuanAn, txtDangBinhLuan;
    private Toolbar toolbar;
    private EditText edTieuDeBinhLuan, edNoiDungBinhLuan, edChamDiem;
    private ImageButton btnChonHinh;
    private RecyclerView recyclerViewChonHinhBinhLuan;
    private AdapterHienThiHinhBinhLuanDuocChon adapterHienThiHinhBinhLuanDuocChon;

    private static final int REQUEST_CHONHINHBINHLUAN = 11;
    private String maquanan;
    private SharedPreferences sharedPreferences;

    private BinhLuanController binhLuanController;
    private List<String> listHinhDuocChon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);

        initViews();
        setupToolbar();
        loadDataFromIntent();
    }

    private void initViews() {
        txtDiaChiQuanAn = findViewById(R.id.txtDiaChiQuanAn);
        txtTenQuanAn = findViewById(R.id.txtTenQuanAn);
        txtDangBinhLuan = findViewById(R.id.txtDangBinhLuan);
        edTieuDeBinhLuan = findViewById(R.id.edTieuDeBinhLuan);
        edNoiDungBinhLuan = findViewById(R.id.edNoiDungBinhLuan);
        edChamDiem = findViewById(R.id.edChamDiem);
        toolbar = findViewById(R.id.toolbar);
        btnChonHinh = findViewById(R.id.btnChonHinh);
        recyclerViewChonHinhBinhLuan = findViewById(R.id.recyclerChonHinhBinhLuan);

        recyclerViewChonHinhBinhLuan.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        listHinhDuocChon = new ArrayList<>();
        binhLuanController = new BinhLuanController();

        btnChonHinh.setOnClickListener(this);
        txtDangBinhLuan.setOnClickListener(this);
    }

    private void setupToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadDataFromIntent() {
        Intent intent = getIntent();
        maquanan = intent.getStringExtra("maquanan");
        String tenquan = intent.getStringExtra("tenquan");
        String diachi = intent.getStringExtra("diachi");

        sharedPreferences = getSharedPreferences("luudangnhap", MODE_PRIVATE);

        txtDiaChiQuanAn.setText(diachi);
        txtTenQuanAn.setText(tenquan);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnChonHinh) {
            Intent iChonHinhBinhLuan = new Intent(this, ChonHinhBinhLuanActivity.class);
            startActivityForResult(iChonHinhBinhLuan, REQUEST_CHONHINHBINHLUAN);
        } else if (v.getId() == R.id.txtDangBinhLuan) {
            submitComment();
        }

    }

    private void submitComment() {
        String tieude = edTieuDeBinhLuan.getText().toString().trim();
        String noidung = edNoiDungBinhLuan.getText().toString().trim();
        Long chamdiem = Long.valueOf(edChamDiem.getText().toString().trim());
        String mauser = sharedPreferences.getString("mauser", "");

        if (tieude.isEmpty() || noidung.isEmpty() || listHinhDuocChon.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields and select at least one image.", Toast.LENGTH_SHORT).show();
            return;
        }

        BinhLuanModel binhLuanModel = new BinhLuanModel();
        binhLuanModel.setTieude(tieude);
        binhLuanModel.setNoidung(noidung);
        binhLuanModel.setChamdiem(chamdiem);
        binhLuanModel.setLuotthich(0);
        binhLuanModel.setMauser(mauser);

        binhLuanController.ThemBinhLuan(maquanan, binhLuanModel, listHinhDuocChon);

        clearInputFields();
        Toast.makeText(this, "Comment submitted successfully!", Toast.LENGTH_SHORT).show();
    }

    private void clearInputFields() {
        edTieuDeBinhLuan.setText("");
        edNoiDungBinhLuan.setText("");
        edChamDiem.setText("");
        listHinhDuocChon.clear();
        if (adapterHienThiHinhBinhLuanDuocChon != null) {
            adapterHienThiHinhBinhLuanDuocChon.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHONHINHBINHLUAN && resultCode == RESULT_OK && data != null) {
            List<String> selectedImages = data.getStringArrayListExtra("listHinhDuocChon");
            if (selectedImages != null) {
                listHinhDuocChon = selectedImages;
                adapterHienThiHinhBinhLuanDuocChon = new AdapterHienThiHinhBinhLuanDuocChon(this, R.layout.custom_layout_hienthibinhluanduocchon, listHinhDuocChon);
                recyclerViewChonHinhBinhLuan.setAdapter(adapterHienThiHinhBinhLuanDuocChon);
                adapterHienThiHinhBinhLuanDuocChon.notifyDataSetChanged();
            }
        }
    }
}
