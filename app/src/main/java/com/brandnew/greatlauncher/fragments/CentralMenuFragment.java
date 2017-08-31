package com.brandnew.greatlauncher.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.brandnew.greatlauncher.R;
import com.brandnew.greatlauncher.activity.AllAppsActivity;
import com.brandnew.greatlauncher.util.Utils;
import com.brandnew.greatlauncher.util.ValueController;

import static com.brandnew.greatlauncher.util.ValueController.OPEN_ALL_APPS;


public class CentralMenuFragment extends Fragment implements View.OnClickListener {
    private ImageButton btnOpenAllApps, btnOpenBrowser, btnOpenCall, btnOpenPhoneSettings;
    private String url = "http://google.com";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_additional_menu, container, false);
        btnOpenAllApps = v.findViewById(R.id.btn_all_apps);
        btnOpenAllApps.setOnClickListener(this);
        btnOpenBrowser = v.findViewById(R.id.btn_browser);
        btnOpenBrowser.setOnClickListener(this);
        btnOpenCall = v.findViewById(R.id.btn_phone);
        btnOpenCall.setOnClickListener(this);
        btnOpenPhoneSettings = v.findViewById(R.id.btn_phone_settings);
        btnOpenPhoneSettings.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_all_apps:
                ValueController.setPointer(OPEN_ALL_APPS);
                startActivity(new Intent(getActivity(), AllAppsActivity.class));
                break;
            case R.id.btn_browser:
                Utils.openBrowser(getActivity(), url);
                break;
            case R.id.btn_phone_settings:
                Utils.openDefaultSettings(getActivity());
                break;
            case R.id.btn_phone:
                Utils.openPhone(getActivity());
                break;
        }
    }

}