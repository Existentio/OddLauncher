package com.brandnew.greatlauncher.util;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.brandnew.greatlauncher.model.AppInfo;

import java.util.List;
import java.util.function.BooleanSupplier;

/**
 * Created by Георгий on 04.09.2017.
 */

public class AppLoader extends AsyncTaskLoader<Boolean> {

    public AppLoader(Context context) {
        super(context);
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public Boolean loadInBackground() {
        new AppManager(getContext()).loadApps();
        return true;
    }

//    @Override
//    public List<AppInfo> loadInBackground() {
////        return new DatabaseHelper(getContext()).getApps(list);
//        return new AppManager(getContext()).loadApps();
//    }
}
