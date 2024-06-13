package com.henrryd.appfoody2.Model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
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

public class QuanAnModel implements Parcelable {
    boolean giaohang;
    String giodongcua, giomocua, tenquanan, videogioithieu, maquanan;
    List<String> tienich;
    List<String> hinhanhquanan;
    List<BinhLuanModel> binhLuanModelList;
    Long luotthich;
    DatabaseReference nodeRoot;

    protected QuanAnModel(Parcel in) {
        giaohang = in.readByte() != 0;
        giodongcua = in.readString();
        giomocua = in.readString();
        tenquanan = in.readString();
        videogioithieu = in.readString();
        maquanan = in.readString();
        tienich = in.createStringArrayList();
        hinhanhquanan = in.createStringArrayList();
        if (in.readByte() == 0) {
            luotthich = null;
        } else {
            luotthich = in.readLong();
        }
    }

    public static final Creator<QuanAnModel> CREATOR = new Creator<QuanAnModel>() {
        @Override
        public QuanAnModel createFromParcel(Parcel in) {
            return new QuanAnModel(in);
        }

        @Override
        public QuanAnModel[] newArray(int size) {
            return new QuanAnModel[size];
        }
    };

    List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList;

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

    public List<ChiNhanhQuanAnModel> getChiNhanhQuanAnModelList() {
        return chiNhanhQuanAnModelList;
    }

    public void setChiNhanhQuanAnModelList(List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList) {
        this.chiNhanhQuanAnModelList = chiNhanhQuanAnModelList;
    }

    public void getDanhSachQuanAn(OdauInterface odauInterface, Location vitrihientai) {
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
                                                        binhLuanModel.setMabinhluan(valueBinhluan.getKey());
                                                        binhLuanModel.setThanhVienModel(thanhVienModel);

                                                        List<String> hinhanhBinhLuanList = new ArrayList<>();
                                                        DatabaseReference hinhanhBinhLuanRef = database.getReference("hinhanhbinhluans").child(binhLuanModel.getMabinhluan());
                                                        hinhanhBinhLuanRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshotNodeHinhAnhBl) {
                                                                for (DataSnapshot valueHinhBinhLuan : snapshotNodeHinhAnhBl.getChildren()) {
                                                                    String hinhAnhBl = valueHinhBinhLuan.getValue(String.class);
                                                                    if (hinhAnhBl != null) {
                                                                        hinhanhBinhLuanList.add(hinhAnhBl);
                                                                    }
                                                                }
                                                                binhLuanModel.setHinhanhBinhLuanList(hinhanhBinhLuanList);
                                                                binhLuanModels.add(binhLuanModel);
                                                                if (binhLuanModels.size() == snapshotBinhLuan.getChildrenCount()) {
                                                                    quanAnModel.setBinhLuanModelList(binhLuanModels);
                                                                    fetchChiNhanhQuanAn(database, quanAnModel, odauInterface, vitrihientai);
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

    private void fetchChiNhanhQuanAn(FirebaseDatabase database, QuanAnModel quanAnModel, OdauInterface odauInterface, Location vitrihientai) {
        DatabaseReference chiNhanhRef = database.getReference("chinhanhquanans").child(quanAnModel.getMaquanan());
        chiNhanhRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotChiNhanh) {
                List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList = new ArrayList<>();
                for (DataSnapshot valueChiNhanhQuanAn : dataSnapshotChiNhanh.getChildren()) {
                    ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChiNhanhQuanAn.getValue(ChiNhanhQuanAnModel.class);
                    if (chiNhanhQuanAnModel != null) {
                        chiNhanhQuanAnModelList.add(chiNhanhQuanAnModel);

                        // Calculate distance
                        Location vitriquanan = new Location("");
                        vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                        vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());
                        float khoangcach = vitrihientai.distanceTo(vitriquanan) / 1000; // in kilometers
                        Log.d("kiemtra", khoangcach + " km - " + chiNhanhQuanAnModel.getDiachi());
                        chiNhanhQuanAnModel.setKhoangcach((double) khoangcach);
                    }
                }
                quanAnModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModelList);
                odauInterface.getDanhSachQuanAnModel(quanAnModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error if any
            }
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeByte((byte) (giaohang ? 1 : 0));
        dest.writeString(giodongcua);
        dest.writeString(giomocua);
        dest.writeString(tenquanan);
        dest.writeString(videogioithieu);
        dest.writeString(maquanan);
        dest.writeStringList(tienich);
        dest.writeStringList(hinhanhquanan);
        if (luotthich == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(luotthich);
        }
    }
}
