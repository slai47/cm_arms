package com.slai.cmarms

import android.content.Context

class PrefUtils {

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

        fun getLayoutStyle(context : Context) : LayoutStyle{
            val prefs = context.getSharedPreferences(PREFERENCE_FIELD, Context.MODE_PRIVATE)
            return LayoutStyle.valueOf(prefs.getString("layout", "STANDARD")!!)
        }

        fun setLayoutStyle(context: Context, mode : LayoutStyle) {
            context.getSharedPreferences(PREFERENCE_FIELD, Context.MODE_PRIVATE).edit().putString("layout", mode.name).apply()
        }
    }

    enum class DayNightMode {
        DAY,
        NIGHT,
        AUTO
    }

    enum class LayoutStyle {
        STANDARD,
        LARGE,
        SMALL,
        TEXT
    }
}