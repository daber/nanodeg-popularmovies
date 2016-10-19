package com.abitcreative.popularmovies;

import android.app.Application;
import android.preference.PreferenceManager;

/**
 * Created by daber on 19/10/16.
 */

public class MyMoviesApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }
}
