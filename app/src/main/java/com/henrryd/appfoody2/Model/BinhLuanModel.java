package com.henrryd.appfoody2.Model;

import java.util.List;

public class BinhLuanModel {
    long chamdiem, luotthich;
    String noidung;

    public String getMabinhluan() {
        return mabinhluan;
    }

    public void setMabinhluan(String mabinhluan) {
        this.mabinhluan = mabinhluan;
    }

    String mabinhluan;

    public List<String> getHinhanhList() {
        return hinhanhList;
    }

    public void setHinhanhList(List<String> hinhanhList) {
        this.hinhanhList = hinhanhList;
    }

    List<String> hinhanhList;

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    String mauser;

    public BinhLuanModel(){

    }
    public long getChamdiem() {
        return chamdiem;
    }

    public void setChamdiem(long chamdiem) {
        this.chamdiem = chamdiem;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public ThanhVienModel getThanhVienModel() {
        return thanhVienModel;
    }

    public void setThanhVienModel(ThanhVienModel thanhVienModel) {
        this.thanhVienModel = thanhVienModel;
    }

    String tieude;
    ThanhVienModel thanhVienModel;

}
