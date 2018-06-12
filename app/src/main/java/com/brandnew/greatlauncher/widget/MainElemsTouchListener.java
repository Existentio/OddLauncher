package com.brandnew.greatlauncher.widget;

import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.brandnew.greatlauncher.R;
import com.brandnew.greatlauncher.activity.HomeActivity;
import com.brandnew.greatlauncher.fragments.CentralMenuFragment;
import com.brandnew.greatlauncher.util.AnimHelper;

/**
 * Created by Георгий on 26.08.2017.
 */

public class MainElemsTouchListener implements TouchListener {
    static final String logTag = "ActivitySwipeDetector";
    static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;
    private HomeActivity home;
    private CentralMenuFragment centralMenu;
    private View menu;
    private RelativeLayout rl;

    public MainElemsTouchListener(HomeActivity home, CentralMenuFragment
            centralMenu, View menu, RelativeLayout rl) {
        this.home = home;
        this.centralMenu = centralMenu;
        this.menu = menu;
        this.rl = rl;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // swipe horizontal?
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // left or right
                    if (deltaX < 0) {
                        this.onLeftToRightSwipe();
                        return true;
                    }
                    if (deltaX > 0) {
                        this.onRightToLeftSwipe();
                        return true;
                    }
                } else {
//                    Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long horizontally, need at least " + MIN_DISTANCE);
                    // return false; // We don't consume the event
                }

                // swipe vertical?
                if (Math.abs(deltaY) > MIN_DISTANCE) {
                    // top or down
                    if (deltaY < 0) {
                        this.onTopToBottomSwipe();
                        return true;
                    }
                    if (deltaY > 0) {
                        this.onBottomToTopSwipe();
                        return true;
                    }
                } else {
                    if (menu.isShown()) {
                        AnimHelper.makeGone(menu);
                    }
//                    Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long vertically, need at least " + MIN_DISTANCE);
                    // return false; // We don't consume the event
                }
                return false; // no swipe horizontally and no swipe vertically
            }
        }
        return false;
    }


    @Override
    public void onLeftToRightSwipe() {
        home.moveApartMainElems();
    }

    @Override
    public void onRightToLeftSwipe() {
        if (home.mainElemsAreDivided()) {
            home.moveBackMainElems();
            home.mainElemsState(false);
        }
    }

    @Override
    public void onTopToBottomSwipe() {
        //temporally do nothing
    }

    @Override
    public void onBottomToTopSwipe() {
        if ((rl != null) && (!menu.isShown())) {
            AnimHelper.startAnim(menu, R.anim.slide_in);
            AnimHelper.makeVisible(home, menu);
        }
    }
}
