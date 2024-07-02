package com.example.assignment_android_2;

import android.app.Application;

import com.example.assignment_android_2.Data.SharePreferencesManager;
import com.example.assignment_android_2.Utilities.SignalManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharePreferencesManager.init(this);
        SignalManager.init(this);
    }
}
