package com.brandnew.greatlauncher.viewutil;


import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flor on 10.06.2018.
 */

public class UiDataInspector implements StateInspector {

    private static UiDataInspector INSPECTOR = null;
    private List<UiObserver> observers;
    private boolean state;

    private UiDataInspector() {
        observers = new ArrayList<>();
    }

    private void setData(boolean state) {
        this.state = state;
        notifyObservers();
    }

    private void updateViewInfo() {
        new Handler().postDelayed(() -> setData(false), 3000);
    }

    public static UiDataInspector getInstance() {
        return (INSPECTOR == null) ? INSPECTOR = new UiDataInspector() : INSPECTOR;
    }

    @Override
    public void registerObserver(UiObserver uiObserver) {
        if (!observers.contains(uiObserver)) {
            observers.add(uiObserver);
        }

    }

    @Override
    public void removeObserver(UiObserver uiObserver) {
        if (observers.contains(uiObserver)) {
            observers.remove(uiObserver);
        }
    }

    @Override
    public void notifyObservers() {
        for (UiObserver observer : observers)
            observer.onDataChanged(state);
    }
}
