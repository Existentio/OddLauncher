package com.brandnew.greatlauncher.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.brandnew.greatlauncher.R;
import com.brandnew.greatlauncher.model.AppInfo;
import com.brandnew.greatlauncher.util.AppAdapter;
import com.brandnew.greatlauncher.util.AppManager;
import com.brandnew.greatlauncher.util.OnSearchListener;
import com.brandnew.greatlauncher.util.Utils;
import com.brandnew.greatlauncher.widget.SearchWatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DefaultAppsActivity extends AppCompatActivity implements OnSearchListener {

    private LinearLayout frameContainer;
    private RecyclerView rvDefault;
    private AppAdapter adapter;
    private AppManager manager;
    private EditText searchText;
    private FrameLayout flSearch;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_apps);

        initUI();
        onSearch();
    }

    public void initUI() {
//        searchText = (EditText) findViewById(R.id.search_bar_default);
        frameContainer = (LinearLayout) findViewById(R.id.frame_default);
        flSearch = (FrameLayout) findViewById(R.id.frame_default_search);
        rvDefault = (RecyclerView) findViewById(R.id.list_view_default);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        rvDefault.setLayoutManager(gridLayoutManager);

        layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

    }


    @Override
    public void onSearch() {
        loadSearchApps();
//        SearchWatcher searchWatcher = new SearchWatcher.Builder()
//                .recyclerView(rvDefault)
//                .activity(DefaultAppsActivity.this)
//                .adapter(adapter)
//                .frameLayout(flSearch)
//                .arrayList(allApps)
//                .layoutManager(layoutManager)
//
//                //TODO при анкомменте будет определять как open_default и следовательно мы не сможем добавить выбранное приложение в БД.
////                .specificValue(result) //was 6
//                .build();
//        searchText.addTextChangedListener(searchWatcher);
    }

    @Override
    public void loadSearchApps() {
        adapter = new AppAdapter(this, AppManager.apps);
        manager = new AppManager(this);
        manager.loadApps();
        rvDefault.setAdapter(adapter);
    }
}

