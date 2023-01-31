package com.example.online_attendance_indtechsoft.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {
    private static String token = null;

    private static String id;
    private static Context context;

    public  PreferenceUtils(){

    }

    public static boolean saveToken(String token, Context context) {
        PreferenceUtils.token = token;
        PreferenceUtils.context = context;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_TOKEN, token);
        prefsEditor.apply();
        return true;
    }

    public static String getToken(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_TOKEN, null);
    }



    public static boolean saveid(String id, Context context) {
        PreferenceUtils.id = id;
        PreferenceUtils.context = context;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_ID, id);
        prefsEditor.apply();
        return true;
    }

    public static String getid(String id, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_ID, null);
    }





}
