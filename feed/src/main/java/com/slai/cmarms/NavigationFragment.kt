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
import com.slai.cmarms.model.Post
import kotlinx.android.synthetic.main.fragment_nav.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class NavigationFragment : Fragment() {

    var setup = false

    val fragments = ArrayList<Fragment>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nav, container, false)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
        setupNavigation()
    }

    fun setupNavigation(){
        if(!setup) {


            navigation.addItem(
                BottomNavigationItem(
                    R.drawable.ic_dashboard,
                    R.string.title_home
                ).setActiveColorResource(R.color.colorAccent)
            )
            navigation.addItem(
                BottomNavigationItem(R.drawable.ic_filter, R.string.title_search).setActiveColorResource(
                    R.color.colorAccent
                )
            )
            navigation.addItem(
                BottomNavigationItem(
                    R.drawable.ic_settings,
                    R.string.title_settings
                ).setActiveColorResource(R.color.colorAccent)
            )

            navigation.initialise()
            setup = true

            if(fragments.size == 0) {
                fragments.add(FeedFragment())
                fragments.add(FilterFragment())
                fragments.add(SettingsFragment())
            }

            navigation.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
                override fun onTabReselected(position: Int) {
                }

                override fun onTabUnselected(position: Int) {
                }

                override fun onTabSelected(position: Int) {
                    val replace: Boolean
                    val currentFrag = fragmentManager!!.findFragmentById(R.id.nav_container)

                    replace = when (position) {
                        0 -> {
                            currentFrag !is FeedFragment
                        }
                        1 -> {
                            currentFrag !is FilterFragment
                        }
                        else -> {
                            currentFrag !is SettingsFragment
                        }
                    }

                    if (replace)
                        fragmentManager!!.beginTransaction().replace(R.id.nav_container, fragments[position]).commit()
                }
            })
            navigation.selectTab(0)
        }
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onNavigationEvent(event : NavigationTransitionEvent) {
        when (event.navTo){
            NavigationEvent.FEED -> navigation.selectTab(0)
            NavigationEvent.SEARCH -> navigation.selectTab(1)
            NavigationEvent.SETTINGS -> navigation.selectTab(2)
        }
    }

}