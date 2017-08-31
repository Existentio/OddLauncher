package com.brandnew.greatlauncher.widget;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Георгий on 24.08.2017.
 */

public interface TouchListener extends View.OnTouchListener {

    @Override
    boolean onTouch(View view, MotionEvent event);

    void onLeftToRightSwipe();

    void onRightToLeftSwipe();

    void onTopToBottomSwipe();

    void onBottomToTopSwipe();

}
