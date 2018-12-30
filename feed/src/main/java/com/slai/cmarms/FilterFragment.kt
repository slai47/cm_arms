package com.slai.cmarms

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.slai.cmarms.adapters.FilterAdapter
import com.slai.cmarms.model.Filter
import com.slai.cmarms.view.DialogRecyclerView
import com.slai.cmarms.viewmodel.CmarmsViewModel
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FilterFragment : Fragment() {

    lateinit var viewModel : CmarmsViewModel

    val filterDB = FiltersDataHolder()

    val prefs = context?.getSharedPreferences("cmarms", Context.MODE_PRIVATE)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
        viewModel = ViewModelProviders.of(activity!!).get(CmarmsViewModel::class.java)

        setupDialogs()

        filter_search.setOnClickListener {
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
    }

    private fun setupDialogs() {
        filter_category.setOnClickListener {
            val adapter = FilterAdapter(context!!, filterDB.categories, true, "category")
            val builder = AlertDialog.Builder(context)
            builder.setTitle(getString(R.string.categories_title))
            builder.setView(DialogRecyclerView(context!!, adapter))
            builder.setPositiveButton(getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
            }
            builder.setOnDismissListener {
                updateButtonText()
            }
            builder.show()
        }
        filter_caliber.setOnClickListener {
            val adapter = FilterAdapter(context!!, filterDB.calibers)
            val builder = AlertDialog.Builder(context)
            builder.setTitle(getString(R.string.caliber_title))
            builder.setView(DialogRecyclerView(context!!, adapter))
            builder.setPositiveButton(getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
            }
            builder.setOnDismissListener {
                updateButtonText()
            }
            builder.show()
        }
        filter_firearm_type.setOnClickListener {
            val adapter = FilterAdapter(context!!, filterDB.firearmTypes, true, "firearm_type")
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Firearm Type")
            builder.setView(DialogRecyclerView(context!!, adapter))
            builder.setPositiveButton(getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
            }
            builder.setOnDismissListener {
                updateButtonText()
            }
            builder.show()
        }
        filter_action_type.setOnClickListener {
            val adapter = FilterAdapter(context!!, filterDB.actions, true, "action_type")
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Action Type")
            builder.setView(DialogRecyclerView(context!!, adapter))
            builder.setPositiveButton(getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
            }
            builder.setOnDismissListener {
                updateButtonText()
            }
            builder.show()
        }

    }

    private fun updateButtonText() {
        val category = prefs?.getString("category", "")
        var defaultCategoryStr = getString(R.string.select_category)
        if(category!!.isNotEmpty())
            defaultCategoryStr = "${getString(R.string.select_category)}\n$category"

        filter_category.text = defaultCategoryStr

        val firearmType = prefs?.getString("firearm_type", "")
        var defaultFirearmType = getString(R.string.select_firearm_type)
        if(firearmType!!.isNotEmpty())
            defaultFirearmType = "${getString(R.string.select_firearm_type)}\n$firearmType"
        filter_firearm_type.text = defaultFirearmType

        val actionType = prefs?.getString("action_type", "")
        var defaultActionType = getString(R.string.select_action_type)
        if(actionType!!.isNotEmpty())
            defaultActionType = "${getString(R.string.select_action_type)}\n$actionType"
        filter_action_type.text = defaultActionType
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onFilterChange(filter : Filter) {
//        Snackbar.make(filter_list, "${filter.value} was changed", Snackbar.LENGTH_SHORT)
        GlobalScope.launch {
            if(prefs!!.getBoolean("", false)){
                viewModel.query.filters[filter.value] = filter
            } else {
                viewModel.query.filters.remove(filter.value)
            }
        }
    }

}