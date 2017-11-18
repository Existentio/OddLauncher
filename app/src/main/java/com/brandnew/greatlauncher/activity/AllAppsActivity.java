package com.brandnew.greatlauncher.activity;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.brandnew.greatlauncher.R;
import com.brandnew.greatlauncher.model.AppInfo;
import com.brandnew.greatlauncher.util.AppAdapter;
import com.brandnew.greatlauncher.util.AppLoader;
import com.brandnew.greatlauncher.util.AppManager;
import com.brandnew.greatlauncher.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllAppsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Boolean> {
    private LinearLayout appsContainer;
    private RecyclerView rvAllApps;
    private AppAdapter adapter;
    private AppManager manager;
    private List<AppInfo> apps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_apps);

        initUI();
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this).forceLoad();

    }

    public void initUI() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        int numOfColumns = Utils.calculateNoOfColumns(this) + 2;

        appsContainer = (LinearLayout) findViewById(R.id.frame_all_top);
        rvAllApps = (RecyclerView) findViewById(R.id.rv_all_apps);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, numOfColumns, GridLayoutManager.VERTICAL, false);
        rvAllApps.setLayoutManager(gridLayoutManager);
    }

    @Override
    public Loader<Boolean> onCreateLoader(int i, Bundle bundle) {
        return new AppLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean b) {
//        adapter = new AppAdapter(this, manager.loadApps(apps));
        adapter = new AppAdapter(this, AppManager.apps);
        rvAllApps.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {
        adapter.notifyDataSetChanged();
    }
}
