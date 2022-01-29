package com.mcal.apksignkill;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static SharedPreferences preferences;

    public static Context getContext() {
        if (context == null) {
            context = new App();
        }
        return context;
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }
}