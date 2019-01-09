package com.slai.cmarms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
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
        setupNavigation()
        val fragment = fragmentManager!!.findFragmentById(R.id.nav_container)
        if(fragment == null)
            fragmentManager!!.beginTransaction().add(R.id.nav_container, FeedFragment()).commit()
    }

    fun setupNavigation(){
        navigation.addItem(BottomNavigationItem(R.drawable.ic_dashboard, R.string.title_home).setActiveColorResource(R.color.colorPrimary))
        navigation.addItem(BottomNavigationItem(R.drawable.ic_filter, R.string.title_search).setActiveColorResource(R.color.colorPrimary))
        navigation.addItem(BottomNavigationItem(R.drawable.ic_settings, R.string.title_settings).setActiveColorResource(R.color.colorPrimary))

        navigation.initialise()
        navigation.selectTab(0)
        navigation.setTabSelectedListener( object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                val replace : Boolean
                val frag : Fragment
                val currentFrag = fragmentManager!!.findFragmentById(R.id.nav_container)

                if(position == 0) {
                    frag = FeedFragment()
                    replace = currentFrag !is FeedFragment
                } else if (position == 1){
                    frag = FilterFragment()
                    replace = currentFrag !is FilterFragment
                } else {
                    frag = SettingsFragment()
                    replace = currentFrag !is SettingsFragment
                }

                if(replace)
                    fragmentManager!!.beginTransaction().replace(R.id.nav_container, frag!!).commit()
            }
        })
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val replace : Boolean
        val frag : Fragment
        val currentFrag = fragmentManager!!.findFragmentById(R.id.nav_container)

        if(item.itemId == R.id.navigation_home) {
            frag = FeedFragment()
            replace = currentFrag !is FeedFragment
        } else if (item.itemId == R.id.navigation_search){
            frag = FilterFragment()
            replace = currentFrag !is FilterFragment
        } else {
            frag = SettingsFragment()
            replace = currentFrag !is SettingsFragment
        }

        if(replace)
            fragmentManager!!.beginTransaction().replace(R.id.nav_container, frag!!).commit()
        return@OnNavigationItemSelectedListener true
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onNavigationEvent(event : NavigationTransitionEvent) {
//        when (event.navTo){
//            NavigationEvent.FEED -> navigation.selectedItemId = R.id.navigation_home
//            NavigationEvent.SEARCH -> navigation.selectedItemId = R.id.navigation_search
//            NavigationEvent.SETTINGS -> navigation.selectedItemId = R.id.navigation_settings
//        }
    }

}