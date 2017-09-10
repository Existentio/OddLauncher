package com.brandnew.greatlauncher;

import android.app.Application;

/**
 * Created by Георгий on 06.09.2017.
 */

public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        instance = ((MyApplication) getApplicationContext()); //was this

    }

}
