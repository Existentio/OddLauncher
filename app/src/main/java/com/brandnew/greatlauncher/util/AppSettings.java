package com.brandnew.greatlauncher.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Георгий on 26.08.2017.
 */

public class AppSettings extends AppCompatActivity {

    public void returnMainPrefs() {
        SharedPreferences spLeftList = getSharedPreferences(SettingsHelper.PREF_LEFT_LIST,
                Context.MODE_PRIVATE);
        SharedPreferences spRightList = getSharedPreferences(SettingsHelper.PREF_RIGHT_LIST,
                Context.MODE_PRIVATE);
        SharedPreferences spSearchBar = getSharedPreferences(SettingsHelper.PREF_SEARCHBAR,
                Context.MODE_PRIVATE);
        SharedPreferences spSearchList = getSharedPreferences(SettingsHelper.PREF_SEARCHLIST,
                Context.MODE_PRIVATE);
        SharedPreferences spMenu = getSharedPreferences(SettingsHelper.PREF_MENU,
                Context.MODE_PRIVATE);
        SharedPreferences spLeftListText = getSharedPreferences(SettingsHelper.PREF_LEFT_LIST_TEXT,
                Context.MODE_PRIVATE);
    }


}
