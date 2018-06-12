package com.brandnew.greatlauncher.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Георгий on 05.07.2017.
 */

public class AppInfo {
    private String name;
    private String code;
    private Drawable icon;
    private int id;

    public AppInfo(String name, String code, Drawable icon, int id) {
        this.name = name;
        this.code = code;
        this.icon = icon;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Drawable getIcon() {
        return icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppInfo appInfo = (AppInfo) o;

        if (!name.equals(appInfo.name)) return false;
        return code.equals(appInfo.code);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }


}
