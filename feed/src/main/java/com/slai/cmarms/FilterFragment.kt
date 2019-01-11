package com.slai.cmarms

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.location.Address
import android.location.Location
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.slai.cmarms.adapters.FilterAdapter
import com.slai.cmarms.backend.GeocoderApi
import com.slai.cmarms.model.*
import com.slai.cmarms.view.DialogRecyclerView
import com.slai.cmarms.viewmodel.CmarmsViewModel
import kotlinx.android.synthetic.main.fragment_nav.*
import kotlinx.android.synthetic.main.snippet_filter.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FilterFragment : Fragment() {

    val REQUEST_CODE = 47
    val HAS_CHECKED_LOCATION = "hasCheckedLocationPermissions1"

    val viewModel by lazy { ViewModelProviders.of(activity!!).get(CmarmsViewModel::class.java) }

    private lateinit var fusedLocationClient : FusedLocationProviderClient

    private val filterDB = FiltersDataHolder()

    lateinit var geocoderApi : GeocoderApi

    var permissionsAskSnackbar : Snackbar? = null

    val pref by lazy { context!!.getSharedPreferences(Utils.PREFERENCE_FIELD, Context.MODE_PRIVATE) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)

        geocoderApi = GeocoderApi(context!!)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

        setupDialog(filter_category, filterDB.categories, getString(R.string.categories_title), Utils.PREF_CATEGORY)
        setupDialog(filter_caliber, filterDB.calibers, getString(R.string.caliber_title), null)
        setupDialog(filter_firearm_type, filterDB.firearmTypes, getString(R.string.firearm_type_title), Utils.PREF_FIREARM_TYPE)
        setupDialog(filter_action_type, filterDB.actions, getString(R.string.action_type_title), Utils.PREF_ACTION_TYPE)

        setupSearchReset()
        fillQueryEditTexts()
        updateButtonText(FilterDialogClosed(""))

        setupLocation()
    }

    private fun setupLocation() {
        if(viewModel.query.location?.isNullOrEmpty())
            checkLocationPermissionsOrGetLocation()

        updateLocationButton(true)

        filter_location_icon.setOnClickListener {
            updateLocationButton(true)
            checkLocationPermissionsOrGetLocation()
        }
    }

    fun updateLocationButton(on : Boolean){
        if(on){
            filter_location_icon.isEnabled = true
            filter_location_icon.setImageResource(R.drawable.ic_location_active)
        } else {
            filter_location_icon.isEnabled = false
            filter_location_icon.setImageResource(R.drawable.ic_location)
        }
    }

    fun checkLocationPermissionsOrGetLocation(){
        val hasCheckedPermission = pref.getBoolean(HAS_CHECKED_LOCATION, false)
        if (!hasCheckedPermission) {
            if(permissionsAskSnackbar == null) {
                permissionsAskSnackbar =
                        Snackbar.make(filter_caliber, getString(R.string.question_location), Snackbar.LENGTH_INDEFINITE)
                permissionsAskSnackbar!!.setAction(getString(R.string.yes)) {
                    askLocationPermission()
                }
                permissionsAskSnackbar!!.show()
            } else if(permissionsAskSnackbar!!.isShown) {
                askLocationPermission()
                permissionsAskSnackbar?.dismiss()
            }
        }  else {
            getLocation()
        }
    }

    fun askLocationPermission(){
        val c = filter_caliber.context
        pref.edit().putBoolean(HAS_CHECKED_LOCATION, true).apply()
        updateLocationButton(false)
        if (ContextCompat.checkSelfPermission(
                c,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE
                )
            } else {
                // Add alert sometime here
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation()
            }
        }
    }

    fun getLocation(){
        updateLocationButton(false)
        fusedLocationClient.lastLocation.addOnSuccessListener {location : Location ->
            geocoderApi.getAddress(location) { address: Address? ->
                if(address != null){
                    val state = address.adminArea
                    filter_location.setText(state)
                } else {
                    Snackbar.make(filter_caliber, "Geocode failed to hit the mark", Snackbar.LENGTH_SHORT).show()
                }
                updateLocationButton(true)
            }
        }.addOnFailureListener {
            Snackbar.make(filter_caliber, "Location didn't find its shot.", Snackbar.LENGTH_SHORT).show()
            updateLocationButton(true)
        }
    }

    private fun fillQueryEditTexts() {
        if(viewModel.query.search.isNotEmpty()){
            filter_search_text.setText(viewModel.query.search)
        }
        if(viewModel.query.location.isNotEmpty()){
            filter_location.setText(viewModel.query.location)
        }
        if(viewModel.query.lowPrice != 0){
            filter_low_price.setText("${viewModel.query.lowPrice}")
        }
        if(viewModel.query.highPrice != 0){
            filter_high_price.setText("${viewModel.query.highPrice}")
        }
    }

    private fun setupSearchReset() {
        filter_search.setOnClickListener {
            setViewModelItems()
            viewModel.clearPosts()
            EventBus.getDefault().post(NavigationTransitionEvent(NavigationEvent.FEED))
        }
        filter_reset.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                viewModel.reset(context!!)
            }
            viewModel.clearPosts()
            val prefs = context?.getSharedPreferences(Utils.PREFERENCE_FIELD, Context.MODE_PRIVATE)!!
            prefs.edit().remove(Utils.PREF_CATEGORY).remove(Utils.PREF_ACTION_TYPE).remove(Utils.PREF_FIREARM_TYPE).apply()
            updateButtonText(FilterDialogClosed(""))
            filter_search_text.text = null
            filter_location.setText(Query.DEFAULT_LOCATION)
            filter_low_price.text = null
            filter_high_price.text = null
            updateLocationButton(true)
        }
    }

    private fun setViewModelItems() {
        val search = filter_search_text.text.toString().trim()
        val lowPrice = filter_low_price.text.toString().trim()
        val highPrice = filter_high_price.text.toString().trim()
        val location = filter_location.text.toString().trim()

        viewModel.query.location = location

        viewModel.query.search = search

        try {
            viewModel.query.lowPrice = lowPrice.toInt()
        } catch (e: Exception) {
            // show error
        }
        try {
            viewModel.query.highPrice = highPrice.toInt()
        } catch (e: Exception) {
            // show error
        }
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

    @Subscribe
    fun updateButtonText(event : FilterDialogClosed) { // optimize to only update the correct one
        val prefs = context?.getSharedPreferences(Utils.PREFERENCE_FIELD, Context.MODE_PRIVATE)!!

        filter_category.apply {
            val category : String = prefs.getString(Utils.PREF_CATEGORY, "")!!
            var defaultCategoryStr = getString(R.string.select_category)
            if(category.isNotEmpty())
                defaultCategoryStr = "${getString(R.string.select_category)}\n$category"
            text = defaultCategoryStr
        }

        filter_firearm_type.apply {
            val firearmType = prefs.getString(Utils.PREF_FIREARM_TYPE, "")!!
            var defaultFirearmType = getString(R.string.select_firearm_type)
            if(firearmType.isNotEmpty())
                defaultFirearmType = "${getString(R.string.select_firearm_type)}\n$firearmType"
            text = defaultFirearmType
        }

        filter_action_type.apply {
            val actionType = prefs.getString(Utils.PREF_ACTION_TYPE, "")!!
            var defaultActionType = getString(R.string.select_action_type)
            if(actionType.isNotEmpty())
                defaultActionType = "${getString(R.string.select_action_type)}\n$actionType"
            text = defaultActionType
        }
    }



    override fun onPause() {
        super.onPause()
        setViewModelItems()
        EventBus.getDefault().unregister(this)
        geocoderApi.dispose()
    }

    @Subscribe
    fun onFilterChange(filter : Filter) {
        val snack = Snackbar.make(nav_container, "${filter.value} was changed", Snackbar.LENGTH_SHORT).show()
        GlobalScope.launch {
            val prefs = context?.getSharedPreferences(Utils.PREFERENCE_FIELD, Context.MODE_PRIVATE)!!
            if(prefs.getBoolean(filter.value, false)){
                viewModel.query.filters[filter.value] = filter
            } else {
                viewModel.query.filters.remove(filter.value)
            }
        }
    }
}