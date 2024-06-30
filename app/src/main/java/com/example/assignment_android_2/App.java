package com.example.assignment_android_2;

import android.app.Application;

import com.example.assignment_android_2.Data.SharePreferencesManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharePreferencesManager.init(this);
    }
}
