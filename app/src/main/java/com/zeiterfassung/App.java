package com.zeiterfassung;

import android.app.Application;

public class App extends Application {
    private static App mContext;

    public static App getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
