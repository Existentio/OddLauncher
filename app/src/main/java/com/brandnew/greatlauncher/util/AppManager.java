package com.brandnew.greatlauncher.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.brandnew.greatlauncher.model.AppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Георгий on 05.07.2017.
 */

public class AppManager {
    private static  List<AppInfo> allApps = new ArrayList<>();
    private static List<AppInfo> leftTableApps = new CustomArrayList<>();
    private static List<AppInfo> rightTableApps = new CustomArrayList<>();
    private static List<AppInfo> appsSearch = new ArrayList<>();
    private static List<AppInfo> returnedList = new ArrayList<>();

    public PackageManager manager;

    //06.12
    private List<AppInfo> mLeftTableApps = leftTableApps;
    private List<AppInfo> mRightTableApps = rightTableApps;
    private List<AppInfo> mDefaultApps = allApps;
    private List<AppInfo> mSearchApps = returnedList;

    public AppManager(Context context) {
        this.manager = context.getPackageManager();
    }

//    public AppManager(Context context, List<AppInfo> allApps) {
//        super();
//    }



    public void loadApps() {
        if (!allApps.isEmpty()) {
            allApps.clear();
        }
        Intent appIntent = new Intent(Intent.ACTION_MAIN, null);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> availableActivities = manager.queryIntentActivities(appIntent, 0);
        fetchInstalledApps(availableActivities);
    }

    private void fetchInstalledApps(List<ResolveInfo> availableActivities) {
        for (ResolveInfo ri : availableActivities) {
            String code = ri.loadLabel(manager).toString();
            String name = ri.activityInfo.packageName;
            Drawable icon = ri.activityInfo.loadIcon(manager);
            AppInfo app = new AppInfo(code, name, icon, 0);
            allApps.add(app);
        }
        Collections.sort(allApps, Utils.NAME_ORDER_ASC);
    }


    /**
     * Method returns filtered apps in search bar
     **/
    public  List<AppInfo> returnRefreshedList(List<AppInfo> list) {
        return returnedList = list;
    }

    @NonNull
    public  String getListName(List<AppInfo> list, int position) {
        return list.get(position).getName().toString();
    }

    @NonNull
    public  String getListCode(List<AppInfo> list, int position) {
        return list.get(position).getCode().toString();
    }

    @NonNull
    public  static int getListId(List<AppInfo> list, int position) {
        return list.get(position).getId();
    }

    public List<AppInfo> listProvider(String key) {
        switch (key) {
            case "left_table":
                return mLeftTableApps;
            case "right_table":
                return mRightTableApps;
            case "all_apps":
                return mDefaultApps;
            case "search_apps":
                return mSearchApps;
        }
        return mDefaultApps;
    }
}
