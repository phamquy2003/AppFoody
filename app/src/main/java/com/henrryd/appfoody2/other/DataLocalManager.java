package com.henrryd.appfoody2.other;

import android.content.Context;

public class DataLocalManager {
    private static final String USER = "USER";
    private static DataLocalManager instance;
    private MysharedPreferences mysharedPreferences;

    private DataLocalManager() {
        // Private constructor to prevent instantiation
    }

    public static synchronized void init(Context context) {
        if (instance == null) {
            instance = new DataLocalManager();
            instance.mysharedPreferences = new MysharedPreferences(context);
        }
    }

    public static synchronized DataLocalManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("DataLocalManager is not initialized. Call init() first.");
        }
        return instance;
    }

    public void updateUser(String value) {
        if (mysharedPreferences == null) {
            throw new IllegalStateException("MysharedPreferences is not initialized.");
        }
        mysharedPreferences.pushStringvalue(USER,value);
    }

    public void removeUser() {
        if (mysharedPreferences == null) {
            throw new IllegalStateException("MysharedPreferences is not initialized.");
        }
        mysharedPreferences.remove(USER);
    }

    public String getUser() {
        if (mysharedPreferences == null) {
            throw new IllegalStateException("MysharedPreferences is not initialized.");
        }
        return mysharedPreferences.getStringvalue(USER);
    }
}
