package com.brandnew.greatlauncher.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;

import com.brandnew.greatlauncher.model.AppInfo;
import static com.brandnew.greatlauncher.util.Constants.*;
import java.util.List;

/**
 * Created by Георгий on 04.07.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "apps.db";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE_LEFT = "lapps";
    private static final String DB_TABLE_RIGHT = "rapps";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME_LEFT = "name";
    private static final String COLUMN_CODE_LEFT = "code";
    private static final String COLUMN_NAME_RIGHT = "name";
    private static final String COLUMN_CODE_RIGHT = "code";

    private static final String DB_CREATE_LEFT =
            "CREATE TABLE " + DB_TABLE_LEFT + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_LEFT + " TEXT, " +
                    COLUMN_CODE_LEFT + " TEXT)";

    private static final String DB_CREATE_RIGHT =
            "CREATE TABLE " + DB_TABLE_RIGHT + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_RIGHT + " TEXT, " +
                    COLUMN_CODE_RIGHT + " TEXT)";

    private static final String SQL_DELETE = "DROP TABLE IF EXISTS ";

    private SQLiteDatabase db;
    private Context context;
    private AppManager appManager;
    private List<AppInfo> arrayListLeft;
    private List<AppInfo> arrayListRight;
    private Cursor cursor;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
        this.context = context;
        appManager = new AppManager(context);
        arrayListLeft = appManager.listProvider(LEFT_TABLE);
        arrayListRight = appManager.listProvider(RIGHT_TABLE);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_LEFT);
        db.execSQL(DB_CREATE_RIGHT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE + DB_TABLE_LEFT);
        db.execSQL(SQL_DELETE + DB_TABLE_RIGHT);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public void addRecLeftTable(String name, String code) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_LEFT, name);
        cv.put(COLUMN_CODE_LEFT, code);
        db.insert(DB_TABLE_LEFT, null, cv);
    }

    public void addRecRightTable(String name, String code) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_RIGHT, name);
        cv.put(COLUMN_CODE_RIGHT, code);
        db.insert(DB_TABLE_RIGHT, null, cv);
    }

    public boolean delRecLeftTable(long id) {
        return db.delete(DB_TABLE_LEFT, COLUMN_ID + "=" + id, null) > 0;
    }

    public boolean delRecRightTable(long id) {
        return db.delete(DB_TABLE_RIGHT, COLUMN_ID + "=" + id, null) > 0;
    }

    public Cursor getAllItems(List<AppInfo> list) {
        if (list == (arrayListLeft)) {
            String[] applications = {COLUMN_ID, COLUMN_NAME_LEFT, COLUMN_CODE_LEFT};
            cursor = db.query(DB_TABLE_LEFT, applications,
                    null,
                    null,
                    null,
                    null,
                    COLUMN_ID + " ASC");
        }
        if (list == (arrayListRight)) {
            String[] applications = {COLUMN_ID, COLUMN_NAME_RIGHT, COLUMN_CODE_RIGHT};
            cursor = db.query(DB_TABLE_RIGHT, applications,
                    null,
                    null,
                    null,
                    null,
                    COLUMN_ID + " ASC");
        }
        return cursor;
    }

    public List<AppInfo> getApps(List<AppInfo> list) {
        try {
            list.clear();
            cursor = getAllItems(list);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String code = cursor.getString(2);
                Drawable icon = context.getPackageManager().getApplicationIcon(code);
                AppInfo appInfo = new AppInfo(name, code, icon, id);
                list.add(appInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }


    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }


}


