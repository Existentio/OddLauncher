package com.brandnew.greatlauncher.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.brandnew.greatlauncher.BaseApplication;
import com.brandnew.greatlauncher.R;
import com.brandnew.greatlauncher.model.AppInfo;
import com.brandnew.greatlauncher.util.AppAdapter;
import com.brandnew.greatlauncher.util.AppManager;
import com.brandnew.greatlauncher.util.OnSearchListener;
import com.brandnew.greatlauncher.util.Utils;
import com.brandnew.greatlauncher.util.ValueController;
import com.brandnew.greatlauncher.widget.SearchWatcher;

import java.util.ArrayList;
import java.util.List;

import static com.brandnew.greatlauncher.util.Constants.*;


public class DefaultAppsActivity extends AppCompatActivity implements OnSearchListener {

    private RelativeLayout frameContainer;
    private RecyclerView rvDefault;
    private AppAdapter adapter;
//    private AppManager manager;
    private EditText searchText;
    private FrameLayout flSearch;
    private RecyclerView.LayoutManager layoutManager;

    private AppManager manager = new AppManager(BaseApplication.get());
    private List<AppInfo> allApps = manager.listProvider(ALL_APPS);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_apps);


        initUI();

        onSearch();

    }

    public void initUI() {
        searchText = (EditText) findViewById(R.id.search_bar_default);
        frameContainer = (RelativeLayout) findViewById(R.id.frame_default);
        flSearch = (FrameLayout) findViewById(R.id.frame_default_search);
        rvDefault = (RecyclerView) findViewById(R.id.list_view_default);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        rvDefault.setLayoutManager(gridLayoutManager);

        layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    @Override
    public void onSearch() {
        String pointer = ValueController.getPointer();
        Log.d("pointer is", pointer);

        loadSearchApps();
        SearchWatcher searchWatcher = new SearchWatcher.Builder()
                .recyclerView(rvDefault)
                .activity(DefaultAppsActivity.this)
                .adapter(adapter)
                .frameLayout(flSearch)
                .arrayList(allApps)
                .layoutManager(layoutManager)
                .specificValue(pointer)
                .build();
        searchText.addTextChangedListener(searchWatcher);
    }

    @Override
    public void loadSearchApps() {
        adapter = new AppAdapter(this, allApps);
        rvDefault.setAdapter(adapter);
    }
}

