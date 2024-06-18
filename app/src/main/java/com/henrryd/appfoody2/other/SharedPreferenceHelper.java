package com.henrryd.appfoody2.other;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.henrryd.appfoody2.Model.DatMon;

import java.lang.reflect.Type;
import java.util.List;

public class SharedPreferenceHelper {
    private static final String PREF_NAME = "AppFoodyPrefs";
    private static final String KEY_DAT_MON_LIST = "datMonList";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public SharedPreferenceHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // Lấy danh sách DatMon từ SharedPreferences
    public List<DatMon> getDatMonList() {
        String json = sharedPreferences.getString(KEY_DAT_MON_LIST, "");
        Type type = new TypeToken<List<DatMon>>() {}.getType();
        return gson.fromJson(json, type);
    }

    // Lưu danh sách DatMon vào SharedPreferences
    public void saveDatMonList(List<DatMon> datMonList) {
        String json = gson.toJson(datMonList);
        sharedPreferences.edit().putString(KEY_DAT_MON_LIST, json).apply();
    }
}
