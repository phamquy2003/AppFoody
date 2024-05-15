package com.henrryd.appfoody2.other;
import java.io.Serializable;

public class user implements Serializable {
    public String name;
    public String username;
    public String Pass;
    public String avatar;

    public user(String name, String username, String pass, String avatar) {
        this.name = name;
        this.username = username;
        this.Pass = pass;
        this.avatar = avatar;
    }
}

