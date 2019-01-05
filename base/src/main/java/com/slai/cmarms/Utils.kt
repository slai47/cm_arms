package com.slai.cmarms

import android.content.Context

class Utils {

    companion object {
        @JvmField val PREFERENCE_FIELD = "cmarms"
        @JvmField val PREF_CATEGORY = "category"
        @JvmField val PREF_ACTION_TYPE = "action_type"
        @JvmField val PREF_FIREARM_TYPE = "firearm_type"

        fun getDayNightMode(context : Context) : DayNightMode{
            val prefs = context.getSharedPreferences(PREFERENCE_FIELD, Context.MODE_PRIVATE)
            return DayNightMode.valueOf(prefs.getString("theme", "AUTO")!!)
        }

        fun setDayNightMode(context: Context, mode : DayNightMode) {
            context.getSharedPreferences(PREFERENCE_FIELD, Context.MODE_PRIVATE).edit().putString("theme", mode.name).apply()
        }
    }

    enum class DayNightMode {
        DAY,
        NIGHT,
        AUTO
    }
}