package com.slai.cmarms

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if(fragment == null)
            supportFragmentManager.beginTransaction().add(R.id.container, FeedFragment()).commit()
    }
}
