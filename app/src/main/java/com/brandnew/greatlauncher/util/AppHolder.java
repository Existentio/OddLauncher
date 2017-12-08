package com.brandnew.greatlauncher.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.brandnew.greatlauncher.BaseApplication;
import com.brandnew.greatlauncher.R;
import com.brandnew.greatlauncher.activity.HomeActivity;
import com.brandnew.greatlauncher.model.AppInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.brandnew.greatlauncher.util.ValueController.*;

/**
 * Holder for default adapter.
 */

public class AppHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected TextView name;
    protected ImageView icon;
    protected PackageManager manager;
    protected int value = ValueController.chooseDatabaseContainer();
    protected List<AppInfo> apps = new ArrayList<>();
    protected AppManager appManager;
    protected List<AppInfo> appsRight;
    protected List<AppInfo> appsLeft;
    protected List<AppInfo> appsForSearch;
    protected String result;
    protected int position;
    protected Context context;
    protected DatabaseHelper db;

    protected Map<String, ArrayList<AppInfo>> map = new HashMap<>();

    public AppHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.app_name);
        icon = itemView.findViewById(R.id.app_icon);


        appManager = new AppManager(BaseApplication.get());
        itemView.setOnClickListener(this);

        manager = appManager.manager;
        apps = appManager.listProvider("all_apps");
        appsRight = appManager.listProvider("right_table");
        appsLeft = appManager.listProvider("left_table");
        appsForSearch = appManager.listProvider("search_apps");
    }

    @Override
    public void onClick(View v) {
        result = ValueController.getPointer();
        position = getAdapterPosition();
        context = v.getContext();

        Intent intent;
        if (result.equals(OPEN_APPS_LEFT)) {
            intent = manager.getLaunchIntentForPackage(appManager.getListCode(appsLeft, position));
            context.startActivity(intent);
        } else if (result.equals(OPEN_APPS_RIGHT)) {
            intent = manager.getLaunchIntentForPackage(appManager.getListCode(appsRight, position));
            context.startActivity(intent);
        } else if (result.equals(ADD_APPS_LEFT) || result.equals(ADD_APPS_RIGHT)
                || result.equals(OPEN_DEFAULT_APPS)) {
            icon.setImageResource(R.drawable.ic_app_added);
            String appName = appManager.getListName(apps, position);
            String appCode = appManager.getListCode(apps, position);

            db = new DatabaseHelper(context);

            //add selected apps to left and right database tables
            if (value == ADD_TO_LEFT_LIST) {
                db.addRecLeftTable(appName, appCode);
            }
            if (value == ADD_TO_RIGHT_LIST) {
                db.addRecRightTable(appName, appCode);
            }
        } else if (result.equals(OPEN_SEARCH_APPS)) {
            intent = manager.getLaunchIntentForPackage(appManager.getListCode(appsForSearch, position));
            context.startActivity(intent);
            HomeActivity.viewLocker();
        } else if (result.equals(OPEN_ALL_APPS)) {
            intent = manager.getLaunchIntentForPackage(appManager.getListCode(apps, position));
            context.startActivity(intent);
        } else throw new IllegalArgumentException("Wrong argument!");
    }

}






