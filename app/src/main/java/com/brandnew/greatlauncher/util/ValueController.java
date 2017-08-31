package com.brandnew.greatlauncher.util;

/**
 * Created by Георгий on 06.08.2017.
 */

/**
 * This class send flags between activities for handling appropriate layout item in adapter.
 */

public class ValueController {
    private static String pointer;
    private static int databaseNumber;
    private static boolean ADDITIONAL_MENU_IS_VISIBLE = false;

    public static final String ADD_APPS_LEFT = "add_apps_left";
    public static final String ADD_APPS_RIGHT = "add_apps_right";
    public static final String OPEN_DEFAULT_APPS = "open_default_apps";
    public static final String OPEN_APPS_LEFT = "open_apps_left";
    public static final String OPEN_APPS_RIGHT = "open_apps_right";
    public static final String OPEN_SEARCH_APPS = "search_apps";
    public static final String OPEN_ALL_APPS = "open_all_apps";
    public static final int ADD_TO_RIGHT_LIST = 0;
    public static final int ADD_TO_LEFT_LIST = 1;


    /**
     * @param pointer set String value for handling adapter items.
     */
    public static void setPointer(String pointer) {
        ValueController.pointer = pointer;
    }

    public static String setPointerSearch(String pointer) {
        return ValueController.pointer = pointer;
    }

    /**
     * @return pointer value.
     */
    public static String getPointer() {
        return pointer;
    }

    /**
     * @return number of database table.
     */
    public static int chooseDatabaseContainer() {
        if (ValueController.getPointer().equals(ADD_APPS_LEFT)) {
            return databaseNumber = 0;
        } else if (ValueController.getPointer().equals(ADD_APPS_RIGHT)) {
            return databaseNumber = 1;
        }
        return databaseNumber;
    }

}


