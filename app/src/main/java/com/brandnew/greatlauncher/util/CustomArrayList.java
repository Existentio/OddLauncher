package com.brandnew.greatlauncher.util;

import java.util.ArrayList;

/**
 * Created by Георгий on 06.08.2017.
 */


/**
 * ArrayList without duplicated items.
 * */

public class CustomArrayList<T> extends ArrayList<T> {
    @Override
    public boolean add(T obj) {
        return (!contains(obj)) ? super.add(obj) : false;
    }
}
