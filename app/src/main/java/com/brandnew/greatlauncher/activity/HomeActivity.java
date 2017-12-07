package com.brandnew.greatlauncher.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.app.LoaderManager.LoaderCallbacks;

import com.brandnew.greatlauncher.BaseApplication;
import com.brandnew.greatlauncher.R;
import com.brandnew.greatlauncher.fragments.CentralMenuFragment;
import com.brandnew.greatlauncher.model.AppInfo;
import com.brandnew.greatlauncher.util.AnimHelper;
import com.brandnew.greatlauncher.util.AppLoader;
import com.brandnew.greatlauncher.util.AppManager;
import com.brandnew.greatlauncher.util.AppearanceAnimator;
import com.brandnew.greatlauncher.util.DatabaseHelper;
import com.brandnew.greatlauncher.util.AppAdapter;
import com.brandnew.greatlauncher.util.SettingsHelper;
import com.brandnew.greatlauncher.util.ValueController;
import com.brandnew.greatlauncher.util.OnSearchListener;
import com.brandnew.greatlauncher.util.Utils;
import com.brandnew.greatlauncher.widget.MainElemsTouchListener;
import com.brandnew.greatlauncher.widget.RecyclerItemSwiper;
import com.brandnew.greatlauncher.widget.SearchWatcher;


import java.util.List;

import static android.support.v7.widget.helper.ItemTouchHelper.*;
import static com.brandnew.greatlauncher.util.ValueController.*;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,
        AppearanceAnimator, OnSearchListener, SharedPreferences.OnSharedPreferenceChangeListener,
        LoaderCallbacks<Boolean> {
    private EditText searchText;
    private LinearLayout searchBar;
    private RecyclerView rvSearch;
    private ImageButton btnLeftElem, btnRightElem, btnSearch, btnAddLeft, btnAddRight,
            btnDecSearch, btnCancelSearch, btnTheme, btnSettings;
    private FrameLayout frameRight, frameLeft;

    private RelativeLayout rlHome;
    private View centralView;
    //    private AppManager manager;
    private DatabaseHelper db;
    private LoaderManager loaderManager;
    private View menu;
    private RecyclerView rvRight, rvLeft;
    private FrameLayout flSearch;


    private AppManager manager = new AppManager(BaseApplication.get());
    private List<AppInfo> arrayListLeft = manager.listProvider("left_table");
    private List<AppInfo> arrayListRight = manager.listProvider("right_table");
    private List<AppInfo> allApps = manager.listProvider("all_apps"); //was .apps

    private AppAdapter adapterLeft = new AppAdapter(this, arrayListLeft);  //was ok
    private AppAdapter adapterRight = new AppAdapter(this, arrayListRight);

    private AppAdapter adapterAllApps;
    private MainElemsTouchListener mainElemsTouchListener;

    private RecyclerView.LayoutManager linearLayoutManager3;
    private SettingsHelper settingsHelper;
    private SettingsHelper settingsHelperSearch;

    public static float ALPHA_VALUE;
    public static float ALPHA_VALUE_SEARCH;
    private static boolean LEFT_IS_OPENED = false;
    private static boolean RIGHT_IS_OPENED = false;
    private static boolean ADDITIONAL_MENU_IS_VISIBLE = false;

    private static final int LOADER_MAIN_ID = 0;

//    private View newFeature;

    public static final String mypreference = "mypref";
    static boolean pointer = false;
    Animation spreading;
    CentralMenuFragment centralMenu;
    GridLayout containerMenu;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();
        onSearch();
        loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_MAIN_ID, null, this).forceLoad();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public void initUI() {
        db = new DatabaseHelper(this);
        rlHome = (RelativeLayout) findViewById(R.id.layout_home);
        rlHome.setOnClickListener(this);
        menu = findViewById(R.id.my_fragment);
        centralView = findViewById(R.id.center_view);
        menu.setVisibility(View.GONE);
        //search
        searchText = (EditText) findViewById(R.id.search_bar_text);
        flSearch = (FrameLayout) findViewById(R.id.frame_search);
        rvSearch = (RecyclerView) findViewById(R.id.rl_search);
        btnCancelSearch = (ImageButton) findViewById(R.id.btn_cancel_search);
        btnCancelSearch.setOnClickListener(this);
        btnDecSearch = (ImageButton) findViewById(R.id.btn_dec_search);
        btnSearch = (ImageButton) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);

        searchBar = (LinearLayout) findViewById(R.id.search_bar_home);
        rvRight = (RecyclerView) findViewById(R.id.list_view_right);
        rvLeft = (RecyclerView) findViewById(R.id.list_view_left);
        frameRight = (FrameLayout) findViewById(R.id.frame_right);
        frameLeft = (FrameLayout) findViewById(R.id.frame_left);
        btnTheme = (ImageButton) findViewById(R.id.btn_theme);
        btnTheme.setOnClickListener(this);
        btnSettings = (ImageButton) findViewById(R.id.btn_settings);
        btnSettings.setOnClickListener(this);
        btnAddLeft = (ImageButton) findViewById(R.id.btn_add_left);
        btnAddLeft.setOnClickListener(this);
        btnAddRight = (ImageButton) findViewById(R.id.btn_add_right);
        btnAddRight.setOnClickListener(this);
        btnLeftElem = (ImageButton) findViewById(R.id.left);
        btnLeftElem.setOnClickListener(this);
        btnRightElem = (ImageButton) findViewById(R.id.right);
        btnRightElem.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager linearLayoutManager2 =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager3 =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRight.setLayoutManager(linearLayoutManager);
        rvLeft.setLayoutManager(linearLayoutManager2);
        rvSearch.setLayoutManager(linearLayoutManager3);

        settingsHelper = new SettingsHelper(this);
        settingsHelperSearch = new SettingsHelper(this);

        //handling swipe action
        new RecyclerItemSwiper(0, LEFT | RIGHT, this,
                adapterLeft, arrayListLeft, db, "left", rvLeft);

        new RecyclerItemSwiper(0, LEFT | RIGHT, this,
                adapterRight, arrayListRight, db, "right", rvRight);

        setupSharedPreferences();
//        setupSharedPreferencesNew();
//        rlHome.setOnTouchListener(new RelativeLayoutTouchListener(this));
        mainElemsTouchListener = new MainElemsTouchListener(this, centralMenu, menu, rlHome);
        rlHome.setOnTouchListener(mainElemsTouchListener);
    }


    @Override
    public void onBackPressed() {
        //do nothing because we don't want to close our launcher
    }

    private void initApps(List<AppInfo> list) {
        db.getApps(list);

        if (arrayListLeft.size() > 0) {
            rvLeft.setAdapter(adapterLeft);
            adapterLeft.notifyDataSetChanged();
        }

        if (arrayListRight.size() > 0) {
            rvRight.setAdapter(adapterRight);
            adapterRight.notifyDataSetChanged();
        }
    }


    private void setupSharedPreferences() {
        // Get all of the values from shared preferences to set it up
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        loadColorFromPreferences(sharedPreferences);
        loadSizeFromSharedPreferences(sharedPreferences);
        loadAlphaFromSharedPreferences(sharedPreferences);
        loadAlphaSearchButton(sharedPreferences);
//        loadFormFromSharedPreferences(sharedPreferences);
        changeUrl(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

//    private void setupSharedPreferencesNew() {
//        // Get all of the values from shared preferences to set it up
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//
//        AppSettings.loadColorFromPreferences(sharedPreferences, this, btnLeftElem, btnRightElem, settingsHelper);
//        AppSettings.loadSizeFromSharedPreferences(sharedPreferences, this, btnLeftElem, btnRightElem, settingsHelper);
//        AppSettings.loadAlphaFromSharedPreferences(sharedPreferences, this, btnLeftElem, btnRightElem, settingsHelper);
//        AppSettings.loadAlphaSearchButton(sharedPreferences, this, btnSearch, settingsHelper);
////        loadFormFromSharedPreferences(sharedPreferences);
//
//        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
//    }

    private void loadColorFromPreferences(SharedPreferences sharedPreferences) {
        settingsHelper.chooseFormLeftElem(sharedPreferences.getString(getString(R.string.pref_color_key),
                getString(R.string.pref_color_initial_value)), btnLeftElem);
        settingsHelper.chooseFormRightElem(sharedPreferences.getString(getString(R.string.pref_color_key_right),
                getString(R.string.pref_color_initial_value)), btnRightElem);
    }

    private void loadSizeFromSharedPreferences(SharedPreferences sharedPreferences) {
        int minSize = Integer.parseInt(sharedPreferences.getString(getString(R.string.pref_size_key),
                getString(R.string.pref_size_default)));
        int minSizeSearch = Integer.parseInt(sharedPreferences.getString(getString(R.string.pref_size_key_search),
                getString(R.string.pref_size_default_search)));
//
//        int newSize = Integer.parseInt(sharedPreferences.getString(getString(R.string.seekbar_size_main_elems),
//                getString(R.string.pref_size_default)));

        settingsHelper.setSizeMainElems(btnLeftElem, minSize);
        settingsHelper.setSizeMainElems(btnRightElem, minSize);
//        settingsHelperSearch.setSizeOtherViews(btnSearch, minSizeSearch);
    }

    private void loadAlphaFromSharedPreferences(SharedPreferences sharedPreferences) {
        float currentAlpha = Float.parseFloat(sharedPreferences.getString(getString(R.string.pref_alpha_key),
                getString(R.string.pref_alpha_default)));
        setAlphaMainElems(currentAlpha);

        settingsHelper.setAlphaMainButtons(btnLeftElem, currentAlpha);
        settingsHelper.setAlphaMainButtons(btnRightElem, currentAlpha);
    }

    private void loadAlphaSearchButton(SharedPreferences sharedPreferences) {
        float currentAlpha = Float.parseFloat(sharedPreferences.getString(getString(R.string.pref_alpha_search_button_key),
                getString(R.string.pref_alpha_search_button_default)));
        setAlphaSearch(currentAlpha);

        settingsHelper.setAlphaSearchButton(btnSearch, currentAlpha);
    }

    public static void setAlphaMainElems(float value) {
        ALPHA_VALUE = value / 10;
    }

    public static float getAlphaMainElems() {
        return ALPHA_VALUE;
    }

    public static void setAlphaSearch(float value) {
        ALPHA_VALUE_SEARCH = value / 10;
    }

    public static float getAlphaSearch() {
        return ALPHA_VALUE_SEARCH;
    }

    public static void setUrl(String homeUrl) {
        CentralMenuFragment.url = homeUrl;
    }

    public static String getUrl() {
        return CentralMenuFragment.url;
    }


    @Override
    protected void onResume() {
        super.onResume();
        initSettings();

        if (viewLocker()) {
            onConstrict();
            AnimHelper.makeGone(frameRight, frameLeft);
            AnimHelper.makeVisibleWithAlpha(btnRightElem, btnLeftElem);
            AnimHelper.makeVisibleWithAlpha(this, btnSearch);
            setStateForLeftElem(false);
            setStateForRightElem(false);
            lockState = false;

            if (getPointer()) {
                mainElemsState(false);
                moveBackMainElems();
            }
        }

        Log.d("appsLeft", String.valueOf(arrayListLeft.size()));
        Log.d("appsRight", String.valueOf(arrayListLeft.size()));
        Log.d("allApps", String.valueOf(allApps.size()));

    }

    private void initSettings() {
        SharedPreferences spLeftList = getSharedPreferences(SettingsHelper.PREF_LEFT_LIST,
                Context.MODE_PRIVATE);
        SharedPreferences spRightList = getSharedPreferences(SettingsHelper.PREF_RIGHT_LIST,
                Context.MODE_PRIVATE);
        SharedPreferences spSearchBar = getSharedPreferences(SettingsHelper.PREF_SEARCHBAR,
                Context.MODE_PRIVATE);
        SharedPreferences spSearchList = getSharedPreferences(SettingsHelper.PREF_SEARCHLIST,
                Context.MODE_PRIVATE);
        SharedPreferences spMenu = getSharedPreferences(SettingsHelper.PREF_MENU,
                Context.MODE_PRIVATE);
        SharedPreferences spSeekbar = getSharedPreferences(SettingsHelper.PREF_SEEKBAR_SIZE_MAIN,
                Context.MODE_PRIVATE);
        SharedPreferences spSeekbarAlphaMain = getSharedPreferences(SettingsHelper.PREF_SEEKBAR_ALPHA_MAIN,
                Context.MODE_PRIVATE);
        SharedPreferences spSeekbarAlphaSearch = getSharedPreferences(SettingsHelper.PREF_SEEKBAR_ALPHA_SEARCH_BUTTON,
                Context.MODE_PRIVATE);
        SharedPreferences spUrl = getSharedPreferences(SettingsHelper.PREF_URL,
                Context.MODE_PRIVATE);

        if (spLeftList.contains(SettingsHelper.KEY_LEFT_LIST)) {
            rvLeft.setBackgroundColor(spLeftList.getInt(SettingsHelper.KEY_LEFT_LIST, 0));
        }

        if (spRightList.contains(SettingsHelper.KEY_RIGHT_LIST)) {
            rvRight.setBackgroundColor(spRightList.getInt(SettingsHelper.KEY_RIGHT_LIST, 0));
        }

        if (spSearchBar.contains(SettingsHelper.KEY_SEARCHBAR)) {
            searchBar.setBackgroundColor(spSearchBar.getInt(SettingsHelper.KEY_SEARCHBAR, 0));
        }

        if (spSearchList.contains(SettingsHelper.KEY_SEARCHLIST)) {
            flSearch.setBackgroundColor(spSearchList.getInt(SettingsHelper.KEY_SEARCHLIST, 0));
        }

        if (spMenu.contains(SettingsHelper.KEY_MENU)) {
            menu.setBackgroundColor(spMenu.getInt(SettingsHelper.KEY_MENU, 0));
        }

        if (spSeekbar.contains(SettingsHelper.KEY_SEEKBAR_SIZE_MAIN)) {
            int result = (spSeekbar.getInt(SettingsHelper.KEY_SEEKBAR_SIZE_MAIN, 0));
            settingsHelper.setSizeMainElems(btnLeftElem, result);
            settingsHelper.setSizeMainElems(btnRightElem, result);
        }

        if (spSeekbarAlphaMain.contains(SettingsHelper.KEY_SEEKBAR_ALPHA_MAIN)) {
            float currentAlpha = (float) (spSeekbarAlphaMain.getInt(SettingsHelper.KEY_SEEKBAR_ALPHA_MAIN, 0));
            setAlphaMainElems(currentAlpha);
            settingsHelper.setAlphaMainButtons(btnLeftElem, currentAlpha);
            settingsHelper.setAlphaMainButtons(btnRightElem, currentAlpha);
        }


        if (spSeekbarAlphaSearch.contains(SettingsHelper.KEY_SEEKBAR_ALPHA_SEARCH_BUTTON)) {
            float currentAlpha = (float) (spSeekbarAlphaSearch.getInt(SettingsHelper.KEY_SEEKBAR_ALPHA_SEARCH_BUTTON, 0));
            setAlphaSearch(currentAlpha);
            settingsHelper.setAlphaSearchButton(btnSearch, currentAlpha);
        }

        if (spUrl.contains(SettingsHelper.KEY_URL)) {
//            CentralMenuFragment.url = (spUrl.getString(SettingsHelper.KEY_URL, ""));
            setUrl(spUrl.getString(SettingsHelper.KEY_URL, ""));
        }


    }


    private void changeUrl(SharedPreferences sharedPreferences) {
        String url = sharedPreferences.getString(getString(R.string.pref_search_url),
                getString(R.string.pref_url_default));
        setUrl(url);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_color_key))) {
            loadColorFromPreferences(sharedPreferences);
        } else if (key.equals(getString(R.string.pref_color_key_right))) {
            loadColorFromPreferences(sharedPreferences);
        } else if (key.equals(getString(R.string.pref_size_key))) {
            loadSizeFromSharedPreferences(sharedPreferences);
        } else if (key.equals(getString(R.string.pref_alpha_key))) {
            loadAlphaFromSharedPreferences(sharedPreferences);
        } else if (key.equals(getString(R.string.pref_alpha_search_button_key))) {
            loadAlphaSearchButton(sharedPreferences);
        } else if (key.equals(getString(R.string.pref_search_url))) {
            changeUrl(sharedPreferences);
        }


        /*else if (key.equals(getString(R.string.pref_search_url))) {
                int minSize = Integer.parseInt(sharedPreferences.getString(getString(R.string.pref_size_key), "1.0"));
                tv.setText(String.valueOf(minSize));
        }*/

        /*else if (key.equals(getString(R.string.pref_size_key_search))) {
            loadSizeFromSharedPreferences(sharedPreferences);
        } else if (key.equals(getString(R.string.pref_size_key_search))) {
            loadSizeFromSharedPreferences(sharedPreferences);
        } */
    }

    private static void setStateForLeftElem(boolean state) {
        LEFT_IS_OPENED = state;
//        if (state) {
//            RIGHT_IS_OPENED = false;
//        }
    }

    private static boolean getStateForLeftElem() {
        return LEFT_IS_OPENED;
    }

    private static void setStateForRightElem(boolean state) {
        RIGHT_IS_OPENED = state;
    }

    private static boolean getStateForRightElem() {
        return RIGHT_IS_OPENED;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.left:
                setStateForLeftElem(true);
                if (!frameRight.isShown() && (!getStateForRightElem())) {
                    ValueController.setPointer(OPEN_APPS_RIGHT);
                    AnimHelper.makeVisible(frameRight);
                    AnimHelper.makeGone(btnRightElem);
                    AnimHelper.makeInvisible(searchBar, btnSearch);
                    AnimHelper.startAnim(frameRight, R.anim.slide_right);
                } else {
                    AnimHelper.makeGone(frameRight);
                    AnimHelper.makeVisibleWithAlpha(btnRightElem);
                    AnimHelper.makeVisibleWithAlpha(this, btnSearch);
                    setStateForLeftElem(false);
                }
                initApps(arrayListRight);
                break;

            case R.id.right:
                setStateForRightElem(true);
                if (!frameLeft.isShown() && (!getStateForLeftElem())) {
                    ValueController.setPointer(OPEN_APPS_LEFT);
                    AnimHelper.makeVisible(frameLeft);
                    AnimHelper.makeGone(btnLeftElem);
                    AnimHelper.makeInvisible(searchBar, btnSearch);
                    AnimHelper.startAnim(frameLeft, R.anim.slide_left);
                } else {
                    AnimHelper.makeGone(frameLeft);
                    AnimHelper.makeVisibleWithAlpha(btnLeftElem);
                    AnimHelper.makeVisibleWithAlpha(this, btnSearch);
                    setStateForRightElem(false);
                }
                initApps(arrayListLeft);
                break;

            case R.id.layout_home:
                AnimHelper.makeGone(menu);
                break;

            case R.id.btn_add_left:
                ValueController.setPointer(ADD_APPS_LEFT);
                overridePendingTransition(0, 0);
                sendIntent(DefaultAppsActivity.class);
                AnimHelper.makeGone(menu);
                break;

            case R.id.btn_add_right:
                ValueController.setPointer(ADD_APPS_RIGHT);
                overridePendingTransition(0, 0);
                sendIntent(DefaultAppsActivity.class);
                AnimHelper.makeGone(menu);
                break;

            case R.id.btn_theme:
                Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
                startActivity(Intent.createChooser(intent, getString(R.string.wallpaper_pick)));
                break;

            case R.id.btn_settings:
                sendIntent(SettingsActivity.class);
                break;

            case R.id.btn_search:
                onExpand();
                break;

            case R.id.btn_cancel_search:
                onConstrict();
                break;

            case R.id.btn_all_apps:
                ValueController.setPointer(OPEN_ALL_APPS);
                sendIntent(AllAppsActivity.class);
                break;
        }
    }


    @Override
    public void onSearch() {
        loadSearchApps();
        String result = ValueController.setPointerSearch(OPEN_SEARCH_APPS);
        SearchWatcher searchWatcher = new SearchWatcher.Builder()
                .recyclerView(rvSearch)
                .activity(HomeActivity.this)
                .adapter(adapterAllApps)
                .frameLayout(flSearch)
                .arrayList(allApps)
                .viewsGone(btnLeftElem, btnRightElem, btnSearch)
                .searchViews(flSearch)
                .layoutManager(linearLayoutManager3)
                .specificValue(result)
                .build();

        searchText.addTextChangedListener(searchWatcher);
    }


    @Override
    public void loadSearchApps() {
//        adapterAllApps = new AppAdapter(this, allApps);
//        manager = new AppManager(this);
//        manager.loadApps();
//        rvSearch.setAdapter(adapterAllApps);

        //temporally do nothing
    }

    @Override
    public void onExpand() {
        searchText.setText("");
        AnimHelper.makeGone(btnSearch);
        AnimHelper.startAnim(searchBar, R.anim.slide_right_long);
        AnimHelper.makeVisible(this, searchBar);
        if (getAdditionalMenuState()) {
            mainElemsTouchListener.onRightToLeftSwipe();
        }
    }

    @Override
    public void onConstrict() {
        Utils.hideKeyboard(HomeActivity.this, searchBar);
        AnimHelper.makeGone(flSearch, searchBar);
        AnimHelper.makeVisibleWithAlpha(btnLeftElem, btnRightElem);
        AnimHelper.makeVisibleWithAlpha(this, btnSearch);
    }

    public void sendIntent(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }


    private boolean isCentralMenuVisible = false;

//    private boolean isCentralMenuCalled() {
//        return isCentralMenuVisible = true;
//    }

    public void moveApartMainElems() {
        ((Runnable) () -> {
            if ((rlHome != null) && (!getPointer())) {
                animMainElems();
            }
        }).run();
        isCentralMenuVisible = true;
    }

    public void animMainElems() {
        spreading = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_left);
        btnLeftElem.startAnimation(spreading);

        Animation animation =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_right);
        btnRightElem.startAnimation(animation);
        btnLeftElem.setClickable(false);
        btnRightElem.setClickable(false);
        spreading.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                mainElemsState(true);
                callCentralMenu();
            }
        });
    }


    //TODO rename this later
    public static void mainElemsState(boolean pointer) {
        HomeActivity.pointer = pointer;
    }

    public static boolean getPointer() {
        return pointer;
    }

    public static void setAdditionalMenuState(boolean state) {
        HomeActivity.ADDITIONAL_MENU_IS_VISIBLE = state;
    }

    public static boolean getAdditionalMenuState() {
        return ADDITIONAL_MENU_IS_VISIBLE;
    }

    public void callCentralMenu() {
        new Thread(() -> {
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            centralMenu = new CentralMenuFragment();
            fragmentTransaction.add(R.id.layout_home, centralMenu, "CENTRAL_MENU");
            fragmentTransaction.commit();
        }).start();
    }

    public void moveBackMainElems() {
        AnimHelper.startAnim(btnLeftElem, R.anim.move_left_return);
        AnimHelper.startAnim(btnRightElem, R.anim.move_right_return);
        centralMenu = (CentralMenuFragment) getFragmentManager().findFragmentByTag("CENTRAL_MENU");
        if (centralMenu != null) {
            getFragmentManager().beginTransaction().remove(centralMenu).commit();
        }
        btnLeftElem.setClickable(true);
        btnRightElem.setClickable(true);
        isCentralMenuVisible = false;
    }


    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        return new AppLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean aBoolean) {
        adapterAllApps = new AppAdapter(this, allApps);
        rvSearch.setAdapter(adapterAllApps);
        adapterAllApps.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapterAllApps.notifyDataSetChanged();
    }


    static boolean lockState = false;

    public static boolean viewLocker() {
        return lockState = true;
    }

}


