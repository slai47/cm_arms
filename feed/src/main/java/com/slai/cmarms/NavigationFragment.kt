package com.slai.cmarms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.slai.cmarms.model.NavigationEvent
import com.slai.cmarms.model.NavigationTransitionEvent
import kotlinx.android.synthetic.main.fragment_nav.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class NavigationFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nav, container, false)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
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

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onNavigationEvent(event : NavigationTransitionEvent) {
        when (event.navTo){
            NavigationEvent.FEED -> navigation.selectedItemId = R.id.navigation_home
            NavigationEvent.SEARCH -> navigation.selectedItemId = R.id.navigation_search
            NavigationEvent.SETTINGS -> navigation.selectedItemId = R.id.navigation_settings
        }
    }
}