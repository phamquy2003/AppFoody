package com.henrryd.appfoody2.Controller;

import com.henrryd.appfoody2.Model.BinhLuanModel;

import java.util.List;


public class BinhLuanController {
    BinhLuanModel binhLuanModel;

    public BinhLuanController(){
        binhLuanModel = new BinhLuanModel();
    }

    public void ThemBinhLuan(String maQuanAn, BinhLuanModel binhLuanModel, List<String> listHinh){
        binhLuanModel.ThemBinhLuan(maQuanAn,binhLuanModel,listHinh);
    }
}
