package com.brandnew.greatlauncher.viewutil;


public interface StateInspector {
    void registerObserver(UiObserver uiObserver);
    void removeObserver(UiObserver uiObserver);
    void notifyObservers();
}
