package com.example.dailyart;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPrefUtil {

    public static void saveStringList(Context ctx, List<String> values, String prefName){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i)).append(",");
        }
        editor.putString(prefName, sb.toString());
        editor.commit();
    }

    public static ArrayList<String> getStringList(Context ctx, String prefName){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        String values =  sharedPreferences.getString(prefName, null);
        if (values == null){
            return null;
        }
        return new ArrayList<String>(Arrays.asList(values.split(",")));
    }
}
