package com.brandnew.greatlauncher.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brandnew.greatlauncher.R;
import com.brandnew.greatlauncher.activity.HomeActivity;
import com.brandnew.greatlauncher.model.AppInfo;
import com.brandnew.greatlauncher.viewutil.DialogHelper;

import java.util.ArrayList;
import java.util.List;

import static com.brandnew.greatlauncher.util.ValueController.*;

/**
 * Holder for default adapter.
 */

public class AppHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected TextView name;
    protected ImageView icon;
    protected PackageManager manager = AppManager.manager;
    protected List<AppInfo> apps = AppManager.apps;
    protected List<AppInfo> appsRight = AppManager.appsRight;
    protected List<AppInfo> appsLeft = AppManager.appsLeft;
    protected int value = ValueController.chooseDatabaseContainer();
    protected ArrayList<AppInfo> appsForSearch = AppManager.returnedList;
    protected List<AppInfo> appsDefault = AppManager.appsAll;
    protected String result;
    protected int position;
    protected Context context;
    protected DatabaseHelper db;

    public AppHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.app_name);
        icon = itemView.findViewById(R.id.app_icon);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        result = ValueController.getPointer();
        position = getAdapterPosition();
        context = v.getContext();

        Intent intent;
        if (result.equals(OPEN_APPS_LEFT)) {
            intent = manager.getLaunchIntentForPackage(AppManager.getListCode(appsLeft, position));
            context.startActivity(intent);
        } else if (result.equals(OPEN_APPS_RIGHT)) {
            intent = manager.getLaunchIntentForPackage(AppManager.getListCode(appsRight, position));
            context.startActivity(intent);
        }  else if (result.equals(ADD_APPS_LEFT) || result.equals(ADD_APPS_RIGHT)
                || result.equals(OPEN_DEFAULT_APPS)) {
            icon.setImageResource(R.drawable.ic_app_added);
            String appName = AppManager.getListName(apps, position);
            String appCode = AppManager.getListCode(apps, position);
            db = new DatabaseHelper(context);

            //add selected apps to left and right database tables
            if (value == ADD_TO_LEFT_LIST) {
                db.addItemLeft(appName, appCode);
            }
            if (value == ADD_TO_RIGHT_LIST) {
                db.addItemRight(appName, appCode);
            }
        } else if (result.equals(OPEN_SEARCH_APPS)) {
            intent = manager.getLaunchIntentForPackage(AppManager.getListCode(appsForSearch, position));
            context.startActivity(intent);
            HomeActivity.viewLocker();
        } else if (result.equals(OPEN_ALL_APPS)) {
            intent = manager.getLaunchIntentForPackage(AppManager.getListCode(apps, position));
            context.startActivity(intent);
        } else throw new IllegalArgumentException("Wrong argument!");
    }

}






