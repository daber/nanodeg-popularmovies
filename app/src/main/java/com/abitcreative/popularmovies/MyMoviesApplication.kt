package com.abitcreative.popularmovies

import android.app.Application
import android.preference.PreferenceManager

/**
 * Created by daber on 16/10/16.
 */

class MyMoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}
