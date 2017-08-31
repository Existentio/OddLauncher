package com.brandnew.greatlauncher.widget;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.FrameLayout;

import com.brandnew.greatlauncher.model.AppInfo;
import com.brandnew.greatlauncher.util.AnimHelper;
import com.brandnew.greatlauncher.util.AppAdapter;
import com.brandnew.greatlauncher.util.AppManager;
import com.brandnew.greatlauncher.util.ValueController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Георгий on 08.08.2017.
 */

public class SearchWatcher implements TextWatcher {

    private RecyclerView rvSearch;
    private Activity activity;
    private AppAdapter adapter;
    private FrameLayout flSearch;
    private List<AppInfo> list;
    private View[] views;
    private View[] searchViews;
    private RecyclerView.LayoutManager layoutManager;
    private String specificValue;

    public static class Builder {
        private RecyclerView rvSearch;
        private Activity activity;
        private AppAdapter adapter;
        private FrameLayout flSearch;
        private List<AppInfo> list;
        private View[] views;
        private View[] searchViews;
        private RecyclerView.LayoutManager layoutManager;
        private String specificValue;

        public Builder recyclerView(RecyclerView item) {
            rvSearch = item;
            return this;
        }

        public Builder activity(Activity item) {
            activity = item;
            return this;
        }

        public Builder adapter(AppAdapter item) {
            adapter = item;
            return this;
        }

        public Builder frameLayout(FrameLayout item) {
            flSearch = item;
            return this;
        }

        public Builder arrayList(List<AppInfo> item) {
            list = item;
            return this;
        }

        public Builder viewsGone(View... item) {
            views = item;
            return this;
        }

        public Builder searchViews(View... item) {
            searchViews = item;
            return this;
        }

        public Builder layoutManager(RecyclerView.LayoutManager item) {
            layoutManager = item;
            return this;
        }

        public Builder specificValue(String item) {
            specificValue = item;
            return this;
        }

        public SearchWatcher build() {
            return new SearchWatcher(this);
        }

    }

    private SearchWatcher(Builder builder) {
        rvSearch = builder.rvSearch;
        activity = builder.activity;
        adapter = builder.adapter;
        flSearch = builder.flSearch;
        list = builder.list;
        views = builder.views;
        searchViews = builder.searchViews;
        layoutManager = builder.layoutManager;
        specificValue = builder.specificValue;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //do nothing
    }

    @Override
    public void afterTextChanged(Editable s) {
        //do nothing
    }

    @Override
    public void onTextChanged(CharSequence query, int start, int before, int count) {
        //setting this value for correct search processing
        ValueController.setPointer(specificValue);
        query = query.toString().toLowerCase();
        final ArrayList<AppInfo> filteredList = new ArrayList<>(); //was non-final
        rvSearch.setLayoutManager(layoutManager);
        adapter = new AppAdapter(activity, filteredList);
        rvSearch.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        AppManager.returnList(filteredList);

        for (int i = 0; i < list.size(); i++) {
            final String text = list.get(i).getName().toString().toLowerCase();
            if (text.startsWith(query.toString())) {
                filteredList.add(list.get(i));
            }
        }

        if (!flSearch.isShown() && query.length() > 0) {
            AnimHelper.makeVisible(flSearch);
            AnimHelper.makeGone(views);
        } else if (flSearch.isShown() && query.equals("")) {
            AnimHelper.makeGone(searchViews);
        }
    }

}
