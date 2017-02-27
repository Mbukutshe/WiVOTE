package com.example.mczondi.wivote;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Jack on 5/18/2016.
 */
public class AppLife extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}