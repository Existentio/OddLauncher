package com.brandnew.greatlauncher.activity;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.brandnew.greatlauncher.R;
import com.brandnew.greatlauncher.util.AppAdapter;
import com.brandnew.greatlauncher.util.AppLoader;
import com.brandnew.greatlauncher.util.AppManager;
import com.brandnew.greatlauncher.util.Utils;

import java.util.Collections;

public class AllAppsActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Boolean> {
    private LinearLayout appsContainer;
    private RecyclerView rvAllApps;
    private AppAdapter adapter;
    private AppManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_apps);

        initUI();
//        loadApps();
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this).forceLoad();
    }

    public void initUI() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

       int numOfColumns =  Utils.calculateNoOfColumns(this) + 1;

        appsContainer = (LinearLayout) findViewById(R.id.frame_all_top);
        rvAllApps = (RecyclerView) findViewById(R.id.rv_all_apps);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, numOfColumns, GridLayoutManager.VERTICAL, false);
        rvAllApps.setLayoutManager(gridLayoutManager);
    }

    public void loadApps() {
        adapter = new AppAdapter(this, AppManager.apps);
        manager = new AppManager(this);
        manager.loadApps();
        Collections.sort(AppManager.apps, Utils.NAME_ORDER_ASC);
        rvAllApps.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
//        Collections.sort(allApps, Utils.NAME_ORDER_ASC); //for ASC order
//        Collections.sort(allApps, Utils.NAME_ORDER_DESC);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public Loader<Boolean> onCreateLoader(int i, Bundle bundle) {
        return new AppLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean aBoolean) {
        adapter = new AppAdapter(this, AppManager.apps);
        Collections.sort(AppManager.apps, Utils.NAME_ORDER_ASC);
        rvAllApps.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {
        adapter.notifyDataSetChanged();
    }
}
