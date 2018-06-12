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
import static com.brandnew.greatlauncher.util.Constants.*;
/**
 * Created by Георгий on 05.07.2017.
 */

public class AppManager {

    private PackageManager manager;

    private static List<AppInfo> allApps = new ArrayList<>();
    private static List<AppInfo> leftTableApps = new CustomArrayList<>();
    private static List<AppInfo> rightTableApps = new CustomArrayList<>();
    private static List<AppInfo> searchedApps = new ArrayList<>();

    public AppManager(Context context) {
        this.manager = context.getPackageManager();
    }

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

    public List<AppInfo> listProvider(String key) {
        switch (key) {
            case LEFT_TABLE:
                return leftTableApps;
            case RIGHT_TABLE:
                return rightTableApps;
            case ALL_APPS:
                return allApps;
            case SEARCH_APPS:
                return searchedApps;
        }
        return allApps;
    }

    /**
     * Method returns filtered apps in search bar
     **/
    public List<AppInfo> returnRefreshedList(List<AppInfo> list) {
        return searchedApps = list;
    }

    @NonNull
    public String getListName(List<AppInfo> list, int position) {
        return list.get(position).getName();
    }

    @NonNull
    public String getListCode(List<AppInfo> list, int position) {
        return list.get(position).getCode();
    }

    @NonNull
    public static int getListId(List<AppInfo> list, int position) {
        return list.get(position).getId();
    }

    public PackageManager getManager() {
        return manager;
    }
}
