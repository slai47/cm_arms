package com.slai.cmarms

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.slai.cmarms.interfaces.IFilter
import com.slai.cmarms.model.FilterDialogClosed
import com.slai.cmarms.model.Query
import com.slai.cmarms.presenters.FilterPresenter
import com.slai.cmarms.viewmodel.CmarmsViewModel
import kotlinx.android.synthetic.main.snippet_filter.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FilterFragment : Fragment(), IFilter {

    private val HAS_CHECKED_LOCATION = "hasCheckedLocationPermissions1"

    private val viewModel by lazy { ViewModelProviders.of(activity!!).get(CmarmsViewModel::class.java) }

    private var permissionsAskSnackbar : Snackbar? = null

    private val presenter = FilterPresenter(this)

    private val pref by lazy { context!!.getSharedPreferences(PrefUtils.PREFERENCE_FIELD, Context.MODE_PRIVATE) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)

        setFilterCategory()

        presenter.createDialogs(filter_category, filter_caliber, filter_firearm_type, filter_action_type)

        presenter.setupSearchReset(filter_search, filter_reset)

        fillQueryEditTexts()

        updateButtonText(FilterDialogClosed(""))

        setupLocation()

        addSearchClear()
    }

    private fun setFilterCategory() {
        filter_category.tag = SelectFilterFragment.EXTRA_CATEGORIES
        filter_caliber.tag = SelectFilterFragment.EXTRA_CALIBERS
        filter_firearm_type.tag = SelectFilterFragment.EXTRA_TYPES
        filter_action_type.tag = SelectFilterFragment.EXTRA_ACTIONS
    }

    private fun addSearchClear() {
        filter_search_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 0)
                    filter_input_search_clear.visibility = View.VISIBLE
                else
                    filter_input_search_clear.visibility = View.GONE
            }
        })
        filter_input_search_clear.setOnClickListener {
            filter_search_text.setText("")
        }
    }

    private fun setupLocation() {
        if(viewModel.query.location.isEmpty())
            checkLocationPermissionsOrGetLocation()

        updateLocationButton(true)

        filter_location_icon.setOnClickListener {
            updateLocationButton(true)
            checkLocationPermissionsOrGetLocation()
        }
    }

    override fun updateLocationButton(on : Boolean){
        if(on){
            filter_location_icon.isEnabled = true
            filter_location_icon.visibility = View.VISIBLE
            filter_location_progress.visibility = View.GONE
        } else {
            filter_location_icon.isEnabled = false
            filter_location_icon.visibility = View.GONE
            filter_location_progress.visibility = View.VISIBLE
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
            presenter.getLocation()
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
                    FilterPresenter.REQUEST_CODE
                )
            } else {
                // Add alert sometime here
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults)
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

    override fun searchReset() {
        updateButtonText(FilterDialogClosed(""))
        filter_search_text.text = null
        filter_location.setText(Query.DEFAULT_LOCATION)
        filter_low_price.text = null
        filter_high_price.text = null
        updateLocationButton(true)
    }

    override fun setViewModelItems() {
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

    @Subscribe
    fun updateButtonText(event : FilterDialogClosed) { // optimize to only update the correct one
        val prefs = context?.getSharedPreferences(PrefUtils.PREFERENCE_FIELD, Context.MODE_PRIVATE)!!

        filter_category.apply {
            val category : String = prefs.getString(PrefUtils.PREF_CATEGORY, "")!!
            var defaultCategoryStr = getString(R.string.select_category)
            if(category.isNotEmpty())
                defaultCategoryStr = "${getString(R.string.select_category)}\n$category"
            text = defaultCategoryStr
        }

        filter_firearm_type.apply {
            val firearmType = prefs.getString(PrefUtils.PREF_FIREARM_TYPE, "")!!
            var defaultFirearmType = getString(R.string.select_firearm_type)
            if(firearmType.isNotEmpty())
                defaultFirearmType = "${getString(R.string.select_firearm_type)}\n$firearmType"
            text = defaultFirearmType
        }

        filter_action_type.apply {
            val actionType = prefs.getString(PrefUtils.PREF_ACTION_TYPE, "")!!
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
        presenter.dispose()
    }

    override fun showSnackbar(message : String){
        Snackbar.make(filter_caliber, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun setLocation(state : String){
        filter_location.setText(state)
    }

    override fun getFragmentActivity(): FragmentActivity {
        return activity!!
    }


}