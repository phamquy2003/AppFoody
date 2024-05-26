package com.henrryd.appfoody2.Controller;

import com.henrryd.appfoody2.Model.ThanhVienModel;

public class DangKyController {
    public void ThemThongTinThanhVienController(ThanhVienModel thanhVienModel, String uid) {
        thanhVienModel.ThemThongTinThanhVien(uid);
    }
}
