package com.henrryd.appfoody2.Model;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.henrryd.appfoody2.Controller.Interfaces.OdauInterface;
import java.util.ArrayList;
import java.util.List;

public class QuanAnModel {
    boolean giaohang;
    String giodongcua, giomocua, tenquanan, videogioithieu, maquanan;
    List<String> tienich;
    List<String> hinhanhquanan;
    List<BinhLuanModel> binhLuanModelList;
    Long luotthich;
    DatabaseReference nodeRoot;

    public QuanAnModel() {
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    // Getters and Setters

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    public Long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(Long luotthich) {
        this.luotthich = luotthich;
    }

    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }

    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public void setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
    }

    public void getDanhSachQuanAn(OdauInterface odauInterface) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("quanans");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot valueQuanAn : snapshot.getChildren()) {
                    QuanAnModel quanAnModel = valueQuanAn.getValue(QuanAnModel.class);
                    if (quanAnModel != null) {
                        quanAnModel.setMaquanan(valueQuanAn.getKey());

                        // Fetch images
                        DatabaseReference hinhAnhRef = database.getReference("hinhanhquanans").child(valueQuanAn.getKey());
                        hinhAnhRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshotHinhQuanAn) {
                                List<String> hinhanhlist = new ArrayList<>();
                                for (DataSnapshot valueHinhQuanAn : dataSnapshotHinhQuanAn.getChildren()) {
                                    String hinhAnh = valueHinhQuanAn.getValue(String.class);
                                    if (hinhAnh != null) {
                                        hinhanhlist.add(hinhAnh);
                                    }
                                }
                                quanAnModel.setHinhanhquanan(hinhanhlist);

                                // Fetch comments
                                DatabaseReference binhluanRef = database.getReference("binhluans").child(quanAnModel.getMaquanan());
                                binhluanRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshotBinhLuan) {
                                        List<BinhLuanModel> binhLuanModels = new ArrayList<>();
                                        for (DataSnapshot valueBinhluan : snapshotBinhLuan.getChildren()) {
                                            BinhLuanModel binhLuanModel = valueBinhluan.getValue(BinhLuanModel.class);
                                            if (binhLuanModel != null) {
                                                // Fetch member details
                                                DatabaseReference thanhVienRef = database.getReference("thanhviens").child(binhLuanModel.getMauser());
                                                thanhVienRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshotThanhVien) {
                                                        ThanhVienModel thanhVienModel = snapshotThanhVien.getValue(ThanhVienModel.class);
                                                        binhLuanModel.setThanhVienModel(thanhVienModel);
                                                        binhLuanModels.add(binhLuanModel);
                                                        if (binhLuanModels.size() == snapshotBinhLuan.getChildrenCount()) {
                                                            quanAnModel.setBinhLuanModelList(binhLuanModels);
                                                            odauInterface.getDanhSachQuanAnModel(quanAnModel);
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                                        // Handle error if any
                                                    }
                                                });
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Handle error if any
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle error if any
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error if any
            }
        });
    }
}
