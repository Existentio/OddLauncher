package com.brandnew.greatlauncher.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.brandnew.greatlauncher.R;
import com.brandnew.greatlauncher.activity.HomeActivity;

/**
 * Created by Георгий on 26.08.2017.
 */

public class AppSettings extends AppCompatActivity {
    private static SharedPreferences spLeftList;
    private static SharedPreferences spRightList;
    private static SharedPreferences spSearchBar;
    private static SharedPreferences spSearchList;
    private static SharedPreferences spMenu;
    private static SharedPreferences spSeekbar;
    private static SharedPreferences spSeekbarAlphaMain;
    private static SharedPreferences spSeekbarAlphaSearch;

    public static void returnHomePreferences(Context context) {
        spLeftList = context.getSharedPreferences(SettingsHelper.PREF_LEFT_LIST,
                Context.MODE_PRIVATE);
        spRightList = context.getSharedPreferences(SettingsHelper.PREF_RIGHT_LIST,
                Context.MODE_PRIVATE);
        spSearchBar = context.getSharedPreferences(SettingsHelper.PREF_SEARCHBAR,
                Context.MODE_PRIVATE);
        spSearchList = context.getSharedPreferences(SettingsHelper.PREF_SEARCHLIST,
                Context.MODE_PRIVATE);
        spMenu = context.getSharedPreferences(SettingsHelper.PREF_MENU,
                Context.MODE_PRIVATE);
        spSeekbar = context.getSharedPreferences(SettingsHelper.PREF_SEEKBAR_SIZE_MAIN,
                Context.MODE_PRIVATE);
        spSeekbarAlphaMain = context.getSharedPreferences(SettingsHelper.PREF_SEEKBAR_ALPHA_MAIN,
                Context.MODE_PRIVATE);
        spSeekbarAlphaSearch = context.getSharedPreferences(SettingsHelper.PREF_SEEKBAR_ALPHA_SEARCH_BUTTON,
                Context.MODE_PRIVATE);

    }

    public static void returnUiWithPreferences(
            RecyclerView rvLeft, RecyclerView rvRight, LinearLayout searchBar, FrameLayout flSearch,
            View menu, SettingsHelper settingsHelper, ImageButton btnLeftElem, ImageButton btnRightElem,
            ImageButton btnSearch) {

        if (spLeftList.contains(SettingsHelper.KEY_LEFT_LIST)) {
            rvLeft.setBackgroundColor(spLeftList.getInt(SettingsHelper.KEY_LEFT_LIST, 0));
        }

        if (spRightList.contains(SettingsHelper.KEY_RIGHT_LIST)) {
            rvRight.setBackgroundColor(spRightList.getInt(SettingsHelper.KEY_RIGHT_LIST, 0));
        }

        if (spSearchBar.contains(SettingsHelper.KEY_SEARCHBAR)) {
            searchBar.setBackgroundColor(spSearchBar.getInt(SettingsHelper.KEY_SEARCHBAR, 0));
        }

        if (spSearchList.contains(SettingsHelper.KEY_SEARCHLIST)) {
            flSearch.setBackgroundColor(spSearchList.getInt(SettingsHelper.KEY_SEARCHLIST, 0));
        }

        if (spMenu.contains(SettingsHelper.KEY_MENU)) {
            menu.setBackgroundColor(spMenu.getInt(SettingsHelper.KEY_MENU, 0));
        }

        if (spSeekbar.contains(SettingsHelper.KEY_SEEKBAR_SIZE_MAIN)) {
            int result = (spSeekbar.getInt(SettingsHelper.KEY_SEEKBAR_SIZE_MAIN, 0));
            settingsHelper.setSizeMainButtons(btnLeftElem, result);
            settingsHelper.setSizeMainButtons(btnRightElem, result);
        }

        if (spSeekbarAlphaMain.contains(SettingsHelper.KEY_SEEKBAR_ALPHA_MAIN)) {
            float currentAlpha = (float) (spSeekbarAlphaMain.getInt(SettingsHelper.KEY_SEEKBAR_ALPHA_MAIN, 0));
            HomeActivity.setAlphaMainElems(currentAlpha);
            settingsHelper.setAlphaMainButtons(btnLeftElem, currentAlpha);
            settingsHelper.setAlphaMainButtons(btnRightElem, currentAlpha);
        }

        if (spSeekbarAlphaSearch.contains(SettingsHelper.KEY_SEEKBAR_ALPHA_SEARCH_BUTTON)) {
            float currentAlpha = (float) (spSeekbarAlphaSearch.getInt(SettingsHelper.KEY_SEEKBAR_ALPHA_SEARCH_BUTTON, 0));
            HomeActivity.setAlphaSearch(currentAlpha);
            settingsHelper.setAlphaSearchButton(btnSearch, currentAlpha);
        }
    }

    public static void loadColorFromPreferences(SharedPreferences sharedPreferences, Context context,
                                                ImageButton btnLeftElem, ImageButton btnRightElem, SettingsHelper settingsHelper) {
        settingsHelper.setColorLeft(sharedPreferences.getString(context.getString(R.string.pref_color_key),
                context.getString(R.string.pref_color_initial_value)), btnLeftElem);
        settingsHelper.setColorRight(sharedPreferences.getString(context.getString(R.string.pref_color_key_right),
                context.getString(R.string.pref_color_initial_value)), btnRightElem);
    }

    public static void loadSizeFromSharedPreferences(SharedPreferences sharedPreferences, Context context,
                                                     ImageButton btnLeftElem, ImageButton btnRightElem, SettingsHelper settingsHelper) {
        int minSize = Integer.parseInt(sharedPreferences.getString(context.getString(R.string.pref_size_key),
                context.getString(R.string.pref_size_default)));
        int minSizeSearch = Integer.parseInt(sharedPreferences.getString(context.getString(R.string.pref_size_key_search),
                context.getString(R.string.pref_size_default_search)));
//
//        int newSize = Integer.parseInt(sharedPreferences.getString(getString(R.string.seekbar_size_main_elems),
//                getString(R.string.pref_size_default)));
        settingsHelper.setSizeMainButtons(btnLeftElem, minSize);
        settingsHelper.setSizeMainButtons(btnRightElem, minSize);
//        settingsHelperSearch.setSizeOtherViews(btnSearch, minSizeSearch);
    }

    public static void loadAlphaFromSharedPreferences(SharedPreferences sharedPreferences, Context context,
                                                      ImageButton btnLeftElem, ImageButton btnRightElem, SettingsHelper settingsHelper) {
        float currentAlpha = Float.parseFloat(sharedPreferences.getString(context.getString(R.string.pref_alpha_key),
                context.getString(R.string.pref_alpha_default)));
        HomeActivity.setAlphaMainElems(currentAlpha);

        settingsHelper.setAlphaMainButtons(btnLeftElem, currentAlpha);
        settingsHelper.setAlphaMainButtons(btnRightElem, currentAlpha);
    }

    public static void loadAlphaSearchButton(SharedPreferences sharedPreferences, Context context,
                                             ImageButton btnSearch, SettingsHelper settingsHelper) {
        float currentAlpha = Float.parseFloat(sharedPreferences.getString(context.getString(R.string.pref_alpha_search_button_key),
                context.getString(R.string.pref_alpha_search_button_default)));
        HomeActivity.setAlphaSearch(currentAlpha);

        settingsHelper.setAlphaSearchButton(btnSearch, currentAlpha);
    }


}
