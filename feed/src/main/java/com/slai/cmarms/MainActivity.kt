package com.slai.cmarms

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDayNightMode()

        setContentView(R.layout.activity_main)

        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if(fragment == null)
            supportFragmentManager.beginTransaction().add(R.id.container, NavigationFragment()).commit()
    }

    fun setDayNightMode() {
        val mode = Utils.getDayNightMode(application)
        var delegateMode = AppCompatDelegate.MODE_NIGHT_AUTO
        when (mode) {
            Utils.DayNightMode.DAY -> delegateMode = AppCompatDelegate.MODE_NIGHT_NO
            Utils.DayNightMode.NIGHT -> delegateMode = AppCompatDelegate.MODE_NIGHT_YES
        }

        AppCompatDelegate.setDefaultNightMode(delegateMode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
