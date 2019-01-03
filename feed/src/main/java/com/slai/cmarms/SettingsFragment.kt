package com.slai.cmarms

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onResume() {
        super.onResume()
        val dayNightPref = findPreference<ListPreference>("dayNight")
        dayNightPref.setOnPreferenceChangeListener { preference, newValue ->
            Log.d(SettingsFragment::class.java.simpleName, "$newValue")
            (activity as MainActivity).setDayNightMode()
            false

        }
        val summaryProvider = Preference.SummaryProvider<ListPreference> {
            when(it.value){
                "DAY" -> "Day"
                "NIGHT" -> "Night"
                else -> "Auto"
            }
        }
        dayNightPref.summaryProvider = summaryProvider
    }




}