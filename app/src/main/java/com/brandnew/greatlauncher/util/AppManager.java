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
    public static List<AppInfo> apps = new ArrayList<>();
    public static List<AppInfo> appsLeft = new CustomArrayList<>();
    public static List<AppInfo> appsRight = new CustomArrayList<>();
    public static List<AppInfo> appsAll = new ArrayList<>();
    public static List<AppInfo> appsSearch = new ArrayList<>();
    public static ArrayList<AppInfo> returnedList = new ArrayList<>();
    public static PackageManager manager;
    public Context context;

    public AppManager(Context context) {
        this.context = context;
        this.manager = context.getPackageManager();
    }

    public void loadApps() {
        if (!apps.isEmpty()) {
            apps.clear();
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
            apps.add(app);
        }
        Collections.sort(apps , Utils.NAME_ORDER_ASC);
    }

//    public List<AppInfo> loadApps(List<AppInfo> apps) {
//        if (!apps.isEmpty()) {
//            apps.clear();
//        }
//        Intent appIntent = new Intent(Intent.ACTION_MAIN, null);
//        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//        List<ResolveInfo> availableActivities = manager.queryIntentActivities(appIntent, 0);
//        //get all installed apps from device
//        fetchInstalledApps(availableActivities);
//        return apps;
//    }

    public static ArrayList<AppInfo> returnList(ArrayList<AppInfo> list) {
        return returnedList = list;
    }

    @NonNull
    public static String getListName(List<AppInfo> list, int position) {
        return list.get(position).getName().toString();
    }

    @NonNull
    public static String getListCode(List<AppInfo> list, int position) {
        return list.get(position).getCode().toString();
    }

    @NonNull
    public static int getListId(List<AppInfo> list, int position) {
        return list.get(position).getId();
    }



}
