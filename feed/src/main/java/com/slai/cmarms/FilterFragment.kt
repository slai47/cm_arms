package com.slai.cmarms

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.slai.cmarms.model.Filter
import com.slai.cmarms.model.NavigationEvent
import com.slai.cmarms.model.NavigationTransitionEvent
import com.slai.cmarms.presenters.FilterPresenter
import com.slai.cmarms.viewmodel.CmarmsViewModel
import kotlinx.android.synthetic.main.fragment_nav.*
import kotlinx.android.synthetic.main.snippet_filter.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FilterFragment : Fragment() {

    val viewModel : CmarmsViewModel by lazy { CmarmsViewModel.getInstance() }
    private val presenter = FilterPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)

        presenter.setupDialogs(context!!, filter_category, filter_caliber, filter_firearm_type, filter_action_type)

        setupSearchReset()
        fillQueryEditTexts()
        updateButtonText()
    }

    private fun fillQueryEditTexts() {
        if(viewModel.query.search.isNotEmpty()){
            filter_search_text.setText(viewModel.query.search.replace("+", " "))
        }
        if(viewModel.query.location.isNotEmpty()){
            filter_search_text.setText(viewModel.query.search)
        }
        if(viewModel.query.lowPrice != 0){
            filter_search_text.setText("${viewModel.query.lowPrice}")
        }
        if(viewModel.query.highPrice != 0){
            filter_search_text.setText("${viewModel.query.highPrice}")
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
            updateButtonText()
            filter_search_text.text = null
            filter_location.text = null
            filter_low_price.text = null
            filter_high_price.text = null
        }
    }

    private fun setViewModelItems() {
        val search = filter_search_text.text.toString().trim().replace(" ", "+")
        val lowPrice = filter_low_price.text.toString().trim()
        val highPrice = filter_high_price.text.toString().trim()
        val location = filter_location.text.toString().trim()

        if(location.isNotEmpty())
            viewModel.query.location = location
        if(location.isNotEmpty())
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

    fun updateButtonText() {
        val prefs = context?.getSharedPreferences(Utils.PREFERENCE_FIELD, Context.MODE_PRIVATE)!!
        val category : String = prefs!!.getString(Utils.PREF_CATEGORY, "")!!
        var defaultCategoryStr = getString(R.string.select_category)
        if(category.isNotEmpty())
            defaultCategoryStr = "${getString(R.string.select_category)}\n$category"

        filter_category.text = defaultCategoryStr

        val firearmType = prefs!!.getString(Utils.PREF_FIREARM_TYPE, "")!!
        var defaultFirearmType = getString(R.string.select_firearm_type)
        if(firearmType.isNotEmpty())
            defaultFirearmType = "${getString(R.string.select_firearm_type)}\n$firearmType"
        filter_firearm_type.text = defaultFirearmType

        val actionType = prefs!!.getString(Utils.PREF_ACTION_TYPE, "")!!
        var defaultActionType = getString(R.string.select_action_type)
        if(actionType.isNotEmpty())
            defaultActionType = "${getString(R.string.select_action_type)}\n$actionType"
        filter_action_type.text = defaultActionType
    }

    override fun onPause() {
        super.onPause()
        setViewModelItems()
        EventBus.getDefault().unregister(this)
        presenter.dispose()
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