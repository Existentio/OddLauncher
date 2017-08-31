package com.brandnew.greatlauncher.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import com.brandnew.greatlauncher.R;
import com.brandnew.greatlauncher.util.SettingsHelper;
import com.brandnew.greatlauncher.viewutil.DialogHelper;


/**
 * Created by Георгий on 12.08.2017.
 */


public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int count = prefScreen.getPreferenceCount();


        // Go through all of the preferences, and set up their preference summary.
        for (int i = 0; i < count; i++) {
            Preference p = prefScreen.getPreference(i);
            //don't need to set up preference summaries for checkbox preferences because
            // they are already set up in xml using summaryOff and summary On
            if (!(p instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummary(p, value);
            }

//            Preference preferenceSearch = findPreference(getString(R.string.pref_size_key_search));
//            Preference alpha = findPreference(getString(R.string.pref_alpha_key));
//            Preference alphaSearch = findPreference(getString(R.string.pref_alpha_search_button_key));
//
//            alpha.setOnPreferenceChangeListener(this);
//            alphaSearch.setOnPreferenceChangeListener(this);
//
//            Preference preferenceForm = findPreference(getString(R.string.pref_form_key));

//            preferenceSearch.setOnPreferenceChangeListener(this);
//            preferenceForm.setOnPreferenceChangeListener(this);

        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Figure out which preference was changed
        Preference preference = findPreference(key);
        if (null != preference) {
            // Updates the summary for the preference
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }
        }
    }

    private void setPreferenceSummary(android.support.v7.preference.Preference preference, String value) {
        if (preference instanceof ListPreference) {
            // For list preferences, figure out the label of the selected value
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                // Set the summary to that label
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else if (preference instanceof EditTextPreference) {
            // For EditTextPreferences, set the summary to the value's simple string representation.
            preference.setSummary(value);
        }
    }



    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        SettingsHelper settingsHelper = new SettingsHelper(getContext());
        String key = preference.getKey();
        if (key.equals(getString(R.string.about_dialog))) {
            DialogHelper dialogHelper = new DialogHelper(getContext());
            dialogHelper.buildAboutDialog();
        }

        if (key.equals((getString(R.string.pref_recycler_left_color)))) {
            settingsHelper.setColorForViews(SettingsHelper.KEY_LEFT_LIST);
            settingsHelper.setPref(SettingsHelper.PREF_LEFT_LIST);
        }
        if (key.equals(getString(R.string.pref_recycler_right_color))) {
            settingsHelper.setColorForViews(SettingsHelper.KEY_RIGHT_LIST);
            settingsHelper.setPref(SettingsHelper.PREF_RIGHT_LIST);
        }
        if (key.equals((getString(R.string.pref_search_bar_color)))) {
            settingsHelper.setColorForViews(SettingsHelper.KEY_SEARCHBAR);
            settingsHelper.setPref(SettingsHelper.PREF_SEARCHBAR);
        }
        if (key.equals((getString(R.string.pref_search_list_color)))) {
            settingsHelper.setColorForViews(SettingsHelper.KEY_SEARCHLIST);
            settingsHelper.setPref(SettingsHelper.PREF_SEARCHLIST);
        }

        if (key.equals((getString(R.string.pref_menu_fragment_color)))) {
            settingsHelper.setColorForViews(SettingsHelper.KEY_MENU);
            settingsHelper.setPref(SettingsHelper.PREF_MENU);
        }

        if (key.equals((getString(R.string.seekbar_size_main_elems)))) {
            settingsHelper.setSeekbarEndpointValue(SettingsHelper.KEY_SEEKBAR_SIZE_MAIN,
                    getString(R.string.seekbar_main_title), 400);
            settingsHelper.setPref(SettingsHelper.PREF_SEEKBAR_SIZE_MAIN);
        }

        if (key.equals((getString(R.string.seekbar_alpha_main_elems)))) {
            settingsHelper.setSeekbarEndpointValue(SettingsHelper.KEY_SEEKBAR_ALPHA_MAIN,
                    getString(R.string.seekbar_main_title), 10);
            settingsHelper.setPref(SettingsHelper.PREF_SEEKBAR_ALPHA_MAIN);
        }

        if (key.equals((getString(R.string.seekbar_alpha_search_button)))) {
            settingsHelper.setSeekbarEndpointValue(SettingsHelper.KEY_SEEKBAR_ALPHA_SEARCH_BUTTON,
                    getString(R.string.seekbar_main_title), 10);
            settingsHelper.setPref(SettingsHelper.PREF_SEEKBAR_ALPHA_SEARCH_BUTTON);
        }

//        if (key.equals((getString(R.string.pref_left_list_text_color)))) {
//            settingsHelper.setColorForViews(SettingsHelper.KEY_LEFT_LIST_TEXT);
//            settingsHelper.setPref(SettingsHelper.PREF_LEFT_LIST_TEXT);
//        }

        return super.onPreferenceTreeClick(preference);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }


}

