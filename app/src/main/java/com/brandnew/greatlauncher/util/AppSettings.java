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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Георгий on 26.08.2017.
 */
public class AppSettings extends SettingsHelper{


    private Context context;

    public AppSettings(Context context) {
        super(context);
        this.context = context;
    }

    public AppSettings(Context context, Map<String, View> hv) {
        super(context);
        this.context = context;
        Map<String, View> hv1 = hv;
    }

    public  void getHomePreferences(RecyclerView rvLeft, RecyclerView rvRight, LinearLayout searchBar, FrameLayout flSearch,
                                    View menu, SettingsHelper settingsHelper, ImageButton btnLeftElem, ImageButton btnRightElem,
                                    ImageButton btnSearch) {
        SharedPreferences spLeftList = context.getSharedPreferences(SettingsHelper.PREF_LEFT_LIST,
                Context.MODE_PRIVATE);
        SharedPreferences spRightList = context.getSharedPreferences(SettingsHelper.PREF_RIGHT_LIST,
                Context.MODE_PRIVATE);
        SharedPreferences spSearchBar = context.getSharedPreferences(SettingsHelper.PREF_SEARCHBAR,
                Context.MODE_PRIVATE);
        SharedPreferences spSearchList = context.getSharedPreferences(SettingsHelper.PREF_SEARCHLIST,
                Context.MODE_PRIVATE);
        SharedPreferences spMenu = context.getSharedPreferences(SettingsHelper.PREF_MENU,
                Context.MODE_PRIVATE);
        SharedPreferences spSeekbar = context.getSharedPreferences(SettingsHelper.PREF_SEEKBAR_SIZE_MAIN,
                Context.MODE_PRIVATE);
        SharedPreferences spSeekbarAlphaMain = context.getSharedPreferences(SettingsHelper.PREF_SEEKBAR_ALPHA_MAIN,
                Context.MODE_PRIVATE);
        SharedPreferences spSeekbarAlphaSearch = context.getSharedPreferences(SettingsHelper.PREF_SEEKBAR_ALPHA_SEARCH_BUTTON,
                Context.MODE_PRIVATE);
        SharedPreferences spUrl = context.getSharedPreferences(SettingsHelper.PREF_URL, Context.MODE_PRIVATE);

        if (spLeftList.contains(SettingsHelper.KEY_LEFT_LIST)) {
            rvLeft.setBackgroundColor(spLeftList.getInt(SettingsHelper.KEY_LEFT_LIST, 0));
            setBackgroundColor(rvLeft, spLeftList.getInt(SettingsHelper.KEY_LEFT_LIST, 0 ));
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
            settingsHelper.setSizeMainElems(btnLeftElem, result);
            settingsHelper.setSizeMainElems(btnRightElem, result);

//            setSizeMainElems((ImageButton)hv.get("btnRightElem"), result);
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

        if (spUrl.contains(SettingsHelper.KEY_URL)) {
            HomeActivity.setUrl(spUrl.getString(SettingsHelper.KEY_URL, ""));
        }
    }




}
