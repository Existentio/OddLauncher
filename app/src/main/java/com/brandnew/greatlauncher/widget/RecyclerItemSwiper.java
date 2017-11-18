package com.brandnew.greatlauncher.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.brandnew.greatlauncher.model.AppInfo;
import com.brandnew.greatlauncher.util.AppAdapter;
import com.brandnew.greatlauncher.util.DatabaseHelper;
import com.brandnew.greatlauncher.viewutil.DialogHelper;

import java.util.List;

/**
 * Created by Георгий on 15.08.2017.
 */

public class RecyclerItemSwiper extends ItemTouchHelper.SimpleCallback {

    private Context context;
    private AppAdapter adapter;
    private List<AppInfo> list;
    private DatabaseHelper db;
    private String tag;
    private ItemTouchHelper itemTouchHelper;

    public RecyclerItemSwiper(int dragDirs, int swipeDirs, Context context,
                              AppAdapter adapter, List<AppInfo> list, DatabaseHelper db, String tag, RecyclerView recyclerView) {
        super(dragDirs, swipeDirs);
        this.context = context;
        this.adapter = adapter;
        this.list = list;
        this.db = db;
        this.tag = tag;
        itemTouchHelper = new ItemTouchHelper(RecyclerItemSwiper.this);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //do nothing
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        DialogHelper deleteItemDialog = new DialogHelper(context);
        deleteItemDialog.buildDeleteDialog(position, adapter, list, tag, db);
    }
}

