package com.slai.cmarms

import android.app.Application
import android.content.Context

class CmarmsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        resetPrefs()
    }

    private fun resetPrefs() {
        val prefs = getSharedPreferences(Utils.PREFERENCE_FIELD, Context.MODE_PRIVATE)
        val editor = prefs.edit()

        editor.remove(Utils.PREF_CATEGORY)
        editor.remove(Utils.PREF_FIREARM_TYPE)
        editor.remove(Utils.PREF_ACTION_TYPE)

        val calibers = FiltersDataHolder().calibers
        for(caliber in calibers) {
            editor.remove(caliber.value)
        }
        editor.apply()
    }
}