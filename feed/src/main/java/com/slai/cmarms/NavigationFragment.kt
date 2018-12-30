package com.slai.cmarms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_nav.*

class NavigationFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nav, container, false)
    }

    override fun onResume() {
        super.onResume()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = fragmentManager!!.findFragmentById(R.id.nav_container)
        if(fragment == null)
            fragmentManager!!.beginTransaction().add(R.id.nav_container, FeedFragment()).commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                fragmentManager!!.beginTransaction().replace(R.id.nav_container, FeedFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                fragmentManager!!.beginTransaction().replace(R.id.nav_container, FilterFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                fragmentManager!!.beginTransaction().replace(R.id.nav_container, SettingsFragment()).commit()

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}