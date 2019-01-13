package com.slai.cmarms.presenters

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.slai.cmarms.FilterFragment
import com.slai.cmarms.FiltersDataHolder
import com.slai.cmarms.PrefUtils
import com.slai.cmarms.R
import com.slai.cmarms.adapters.FilterAdapter
import com.slai.cmarms.backend.GeocoderApi
import com.slai.cmarms.interfaces.IDispose
import com.slai.cmarms.model.*
import com.slai.cmarms.view.DialogRecyclerView
import com.slai.cmarms.viewmodel.CmarmsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class FilterPresenter(val filterFragment: FilterFragment) : IDispose{

    companion object {
        val REQUEST_CODE = 47
    }

    val geocoderApi by lazy { GeocoderApi(filterFragment.context!!) }

    private val filterDB = FiltersDataHolder()

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(filterFragment.activity!!) }

    private val viewModel by lazy { ViewModelProviders.of(filterFragment.activity!!).get(CmarmsViewModel::class.java) }

    override fun dispose() {
        geocoderApi.dispose()
    }

    fun createDialogs(vararg views : Button){
        setupDialog(views[0], filterDB.categories, filterFragment.getString(R.string.categories_title), PrefUtils.PREF_CATEGORY)
        setupDialog(views[1], filterDB.calibers, filterFragment.getString(R.string.caliber_title), null)
        setupDialog(views[2], filterDB.firearmTypes, filterFragment.getString(R.string.firearm_type_title), PrefUtils.PREF_FIREARM_TYPE)
        setupDialog(views[3], filterDB.actions, filterFragment.getString(R.string.action_type_title), PrefUtils.PREF_ACTION_TYPE)
    }

    private fun setupDialog(button : Button, filters : ArrayList<Filter>, title : String, prefName : String?) {
        val context = button.context
        button.setOnClickListener {
            val adapter = FilterAdapter(context, filters, prefName)
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            val recyclerView = DialogRecyclerView(context)
            recyclerView.setAdapter(adapter)
            builder.setView(recyclerView)
            builder.setPositiveButton(context.getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
            }
            builder.setOnDismissListener {
                EventBus.getDefault().post(FilterDialogClosed(title))
            }
            builder.show()
        }
    }

    fun getLocation(){
        filterFragment.updateLocationButton(false)
        fusedLocationClient.lastLocation.addOnSuccessListener {location : Location ->
            geocoderApi.getAddress(location) { address: Address? ->
                if(address != null){
                    val state = address.adminArea
                    filterFragment.setLocation(state)
                } else {
                    filterFragment.showSnackbar(filterFragment.getString(R.string.geocode_missed))
                }
                filterFragment.updateLocationButton(true)
            }
        }.addOnFailureListener {
            filterFragment.showSnackbar(filterFragment.getString(R.string.location_missed))
            filterFragment.updateLocationButton(false)
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
            filterFragment.setViewModelItems()
            viewModel.clearPosts()
            EventBus.getDefault().post(NavigationTransitionEvent(NavigationEvent.FEED))
        }
        reset.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                viewModel.reset(filterFragment.context!!)
            }
            viewModel.clearPosts()
            val prefs = filterFragment.context?.getSharedPreferences(PrefUtils.PREFERENCE_FIELD, Context.MODE_PRIVATE)!!
            prefs.edit().remove(PrefUtils.PREF_CATEGORY).remove(PrefUtils.PREF_ACTION_TYPE).remove(PrefUtils.PREF_FIREARM_TYPE).apply()
            filterFragment.searchReset()
        }
    }
}