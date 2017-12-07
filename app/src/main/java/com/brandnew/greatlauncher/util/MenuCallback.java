//package com.brandnew.greatlauncher.util;
//
//import android.annotation.SuppressLint;
//import android.app.Fragment;
//import android.app.FragmentManager;
//
//import com.brandnew.greatlauncher.R;
//import com.brandnew.greatlauncher.fragments.CentralMenuFragment;
//
///**
// * Created by Flor on 06.12.2017.
// */
//
//public class MenuCallback   {
//
//    public void callCentralMenu() {
//        new Thread(() -> {
//            fragmentManager = getFragmentManager();
//            fragmentTransaction = fragmentManager.beginTransaction();
//            centralMenu = new CentralMenuFragment();
//            fragmentTransaction.add(R.id.layout_home, centralMenu, "CENTRAL_MENU");
//            fragmentTransaction.commit();
//        }).start();
//    }
//
//    public MenuCallback(FragmentManager fragmentManager) {
//        fragmentManager = getFragmentManager();
//    }
//
//}
