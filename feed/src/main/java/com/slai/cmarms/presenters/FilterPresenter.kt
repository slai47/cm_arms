package com.slai.cmarms.presenters

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.slai.cmarms.*
import com.slai.cmarms.adapters.FilterAdapter
import com.slai.cmarms.backend.GeocoderApi
import com.slai.cmarms.interfaces.IDispose
import com.slai.cmarms.interfaces.IFilter
import com.slai.cmarms.model.*
import com.slai.cmarms.viewmodel.CmarmsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class FilterPresenter(val filter : IFilter) : IDispose{

    companion object {
        val REQUEST_CODE = 47
    }

    val geocoderApi by lazy { GeocoderApi(filter.getFragmentActivity()) }

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(filter.getFragmentActivity()) }

    private val viewModel by lazy { ViewModelProviders.of(filter.getFragmentActivity()).get(CmarmsViewModel::class.java) }

    override fun dispose() {
        geocoderApi.dispose()
    }

    fun createDialogs(vararg views : Button) {
        views.forEach {
            it.setOnClickListener {
                val tag = it.tag as String
                val manager = filter.getFragmentManager()
                val bundle = Bundle()
                bundle.putString(SelectFilterFragment.EXTRA_FILTER, tag)
                val fragment = SelectFilterFragment()
                fragment.arguments = bundle
                manager!!.beginTransaction()
                    .setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down)
                    .add(R.id.container, fragment)
                    .addToBackStack(tag).commit()
            }
        }
    }


    fun getLocation(){
        filter.updateLocationButton(false)
        fusedLocationClient.lastLocation.addOnSuccessListener {location : Location ->
            geocoderApi.getAddress(location) { address: Address? ->
                if(address != null){
                    val state = address.adminArea
                    filter.setLocation(state)
                } else {
                    filter.showSnackbar(filter.getString(R.string.geocode_missed))
                }
                filter.updateLocationButton(true)
            }
        }.addOnFailureListener {
            filter.showSnackbar(filter.getString(R.string.location_missed))
            filter.updateLocationButton(false)
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        if(requestCode == REQUEST_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation()
            }
        }
    }

    fun setupSearchReset(search : Button, reset : Button) {
        search.setOnClickListener {
            filter.setViewModelItems()
            viewModel.clearPosts()
            EventBus.getDefault().post(NavigationTransitionEvent(NavigationEvent.FEED))
        }
        reset.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                viewModel.reset(filter.getFragmentActivity())
            }
            viewModel.clearPosts()
            val prefs = filter.getFragmentActivity().getSharedPreferences(PrefUtils.PREFERENCE_FIELD, Context.MODE_PRIVATE)!!
            prefs.edit().remove(PrefUtils.PREF_CATEGORY).remove(PrefUtils.PREF_ACTION_TYPE).remove(PrefUtils.PREF_FIREARM_TYPE).apply()
            filter.searchReset()
        }
    }
}