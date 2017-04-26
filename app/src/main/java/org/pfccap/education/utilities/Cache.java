package org.pfccap.education.utilities;

import android.content.Context;
import android.content.SharedPreferences;

//import com.google.gson.Gson;

/**
 * Created by jggomez on 25-Apr-17.
 */

public class Cache {

    private static String preferenceFileKey = "EKOSAVEPREF";
    private static SharedPreferences sharedPreferences;
    //private static Gson gson;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(preferenceFileKey, Context.MODE_PRIVATE);
        //gson = new Gson();
    }

    public static <T> void saveObject(String key, T value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, "");//gson.toJson(value));
        editor.commit();
    }

    public static void save(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getByKey(String key) {
        return sharedPreferences.getString(key, "");
    }

    public static <T> T getByKey(String key, Class<T> type) {
        String value = sharedPreferences.getString(key, "");
        if (value != "") {
            return (T) "";//gson.fromJson(value, type.getClass());
        }
        return null;
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
