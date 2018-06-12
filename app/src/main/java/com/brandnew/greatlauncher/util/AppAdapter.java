package com.brandnew.greatlauncher.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brandnew.greatlauncher.R;
import com.brandnew.greatlauncher.model.AppInfo;

import java.util.List;

import static com.brandnew.greatlauncher.util.ValueController.ADD_APPS_LEFT;
import static com.brandnew.greatlauncher.util.ValueController.ADD_APPS_RIGHT;
import static com.brandnew.greatlauncher.util.ValueController.OPEN_ALL_APPS;
import static com.brandnew.greatlauncher.util.ValueController.OPEN_APPS_LEFT;
import static com.brandnew.greatlauncher.util.ValueController.OPEN_APPS_RIGHT;
import static com.brandnew.greatlauncher.util.ValueController.OPEN_DEFAULT_APPS;
import static com.brandnew.greatlauncher.util.ValueController.OPEN_SEARCH_APPS;

/**
 * Created by Георгий on 10.07.2017.
 */

public class AppAdapter extends RecyclerView.Adapter<AppHolder> {
    private Context context;
    private List<AppInfo> apps;
    private View view;

    public AppAdapter(Context context, List<AppInfo> apps) {
        this.context = context;
        this.apps = apps;
    }

    @Override
    public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        String result = ValueController.getPointer();

        switch (result) {
            case ADD_APPS_LEFT:
            case ADD_APPS_RIGHT:
            case OPEN_DEFAULT_APPS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item_default, parent, false);
                break;
            case OPEN_APPS_LEFT:
            case OPEN_APPS_RIGHT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item, parent, false);
                break;
            case OPEN_SEARCH_APPS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item_search, parent, false);
                break;
            case OPEN_ALL_APPS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item_all_apps, parent, false);
                break;
        }

        AppHolder holder = new AppHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AppHolder holder, int position) {
        holder.name.setText(apps.get(position).getName());
        holder.icon.setImageDrawable(apps.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return (apps.size() > 0) ? apps.size() : 0;
    }


}
