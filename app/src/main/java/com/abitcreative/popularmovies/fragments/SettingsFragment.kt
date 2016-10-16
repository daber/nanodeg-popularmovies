package com.abitcreative.popularmovies.fragments

import android.os.Bundle
import android.preference.PreferenceFragment
import com.abitcreative.popularmovies.R

/**
 * Created by daber on 16/10/16.
 */

class SettingsFragment : PreferenceFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
    }
}