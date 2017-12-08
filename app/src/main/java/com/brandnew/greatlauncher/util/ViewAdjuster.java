package com.brandnew.greatlauncher.util;

import android.content.Context;
import android.view.View;

/**
 * Created by Flor on 08.12.2017.
 */

public class ViewAdjuster extends View {

    public ViewAdjuster(Context context) {
        super(context);
    }

    public void setBackgroundColor(View view, int color) {
        view.setBackgroundColor(color);
    }



}
