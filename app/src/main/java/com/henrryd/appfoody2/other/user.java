package com.henrryd.appfoody2.other;

import java.io.Serializable;

public class user implements Serializable {
    public String name;
    public String username;
    public String Pass; // Sửa chữ P viết hoa thành p viết thường cho nhất quán
    public String avatar;
    public String role; // Thêm trường role

    public user(String name, String username, String pass, String avatar, String role) {
        this.name = name;
        this.username = username;
        this.Pass = pass;
        this.avatar = avatar;
        this.role = role; // Khởi tạo trường role
    }
}
