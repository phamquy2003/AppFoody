package com.henrryd.appfoody2.other;
import android.content.Context;
import android.net.Uri;
import com.henrryd.appfoody2.R;
import java.lang.reflect.Field;

public class custom_Picture {
    private static String PackageName = null;

    public static void init(Context contextt)
    {
        PackageName = contextt.getPackageName().toString();
    }
    public static Uri getUri(String resName){
        int resId = getResId(resName, R.drawable.class);
        return Uri.parse("android.resource://"  + PackageName + "/" + resId);
    }
    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
