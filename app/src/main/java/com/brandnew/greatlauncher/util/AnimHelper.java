package com.brandnew.greatlauncher.util;

/**
 * Created by Георгий on 15.07.2017.
 */


import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.brandnew.greatlauncher.activity.HomeActivity;
import com.brandnew.greatlauncher.BaseApplication;

/**
 * This class handles various animations.
 */

public class AnimHelper {

    public static void makeVisible(View... views) {
        if (views == null) return;
        for (final View view : views) {
            view.setVisibility(View.VISIBLE);
            view.animate()
                    .alpha(1)
                    .setDuration(200)
                    .setInterpolator(new AccelerateDecelerateInterpolator());
        }
    }

    public static void makeVisibleWithAlpha(View... views) {
        if (views == null) return;
        for (final View view : views) {
            view.setVisibility(View.VISIBLE);
            view.animate()
                    .alpha(HomeActivity.getAlphaMainElems())
                    .setDuration(200)
                    .setInterpolator(new AccelerateDecelerateInterpolator());
        }
    }

    public static void makeVisibleWithAlpha(Context context, View... views) {
        if (views == null) return;
        for (final View view : views) {
            view.setVisibility(View.VISIBLE);
            view.animate()
                    .alpha(HomeActivity.getAlphaSearch())
                    .setDuration(200)
                    .setInterpolator(new AccelerateDecelerateInterpolator());
        }
    }


    public static void makeVisible(Context context, View... views) {
        if (views == null) return;
        for (final View view : views) {
            view.setVisibility(View.VISIBLE);
            view.animate()
                    .alpha(1);
        }
    }

    public static void makeInvisible(View... views) {
        if (views == null) return;
        for (final View view : views) {
            view.animate()
                    .alpha(0)
                    .setDuration(200)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .withEndAction(() -> view.setVisibility(View.INVISIBLE));
        }
    }

    public static void makeGone(View... views) {
        if (views == null) return;
        for (final View view : views) {
            view.animate()
                    .alpha(0)
                    .setDuration(200)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .withEndAction(()-> view.setVisibility(View.GONE));
        }
    }


    public static void startAnim(View view, int animation) {
//        ((Runnable) () -> {
        Animation anim = setAnim(animation);
        view.startAnimation(anim);
//        }).run();
    }

    public static Animation setAnim(int anim) {
        Animation desirableAnimation = AnimationUtils.loadAnimation(BaseApplication.get(), anim);
        return desirableAnimation;
    }


}
