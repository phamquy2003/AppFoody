package com.henrryd.appfoody2.other;

import android.content.Context;
import android.util.Log;

public class DataLocalManager {
    private static final String USER = "USER";
    private static DataLocalManager instance;
    private MysharedPreferences mysharedPreferences;

    public static void init(Context context) {
        if (instance == null) {
            instance = new DataLocalManager();
            instance.mysharedPreferences = new MysharedPreferences(context);
            Log.d("DataLocalManager", "DataLocalManager initialized");
        }
    }

    private static DataLocalManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("DataLocalManager is not initialized, call init(context) method first.");
        }
        return instance;
    }

    public static void update_user(String value) {
        getInstance().mysharedPreferences.pushStringvalue(USER, value);
    }

    public static void remove_user() {
        getInstance().mysharedPreferences.remove(USER);
    }

    public static String get_user() {
        return getInstance().mysharedPreferences.getStringvalue(USER);
    }
}
