package org.pfccap.education.utilities;

import android.content.Context;
import android.content.SharedPreferences;

//import com.google.gson.Gson;

/**
 * Created by jggomez on 25-Apr-17.
 */

public class Cache {

    private static String preferenceFileKey = "FPTC";
    private static SharedPreferences sharedPreferences;
    //private static Gson gson;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(preferenceFileKey, Context.MODE_PRIVATE);
    }

    public static void save(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getByKey(String key) {
        return sharedPreferences.getString(key, "");
    }

    public static void remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void clearAll() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}
