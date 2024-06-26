package com.henrryd.appfoody2.Model;

import java.io.Serializable;
import java.util.List;

public class Quan   implements Serializable {
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

    public List<ThucDonModel> getThucDons() {
        return thucDons;
    }

    public void setThucDons(List<ThucDonModel> thucDons) {
        this.thucDons = thucDons;
    }

    public Long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(Long luotthich) {
        this.luotthich = luotthich;
    }

    boolean giaohang;
    String giodongcua, giomocua, tenquanan, videogioithieu, maquanan;
    List<String> tienich;
    List<String> hinhanhquanan;
    List<BinhLuanModel> binhLuanModelList;



    List<ThucDonModel> thucDons;
    Long luotthich;
}
