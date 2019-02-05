package com.slai.cmarms

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.slai.cmarms.adapters.FilterAdapter
import com.slai.cmarms.model.Filter
import com.slai.cmarms.model.FilterDialogClosed
import com.slai.cmarms.viewmodel.CmarmsViewModel
import kotlinx.android.synthetic.main.fragment_select_filter.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SelectFilterFragment : Fragment() {

    companion object {
        const val EXTRA_FILTER = "filter"
        const val EXTRA_CATEGORIES = "categories"
        const val EXTRA_CALIBERS = "calibers"
        const val EXTRA_TYPES = "types"
        const val EXTRA_ACTIONS = "actions"
    }

    val viewModel by lazy { ViewModelProviders.of(activity!!).get(CmarmsViewModel::class.java) }

    lateinit var manager : LinearLayoutManager
    var adapter : FilterAdapter? = null

    private val filterDB = FiltersDataHolder()

    lateinit var filterBy : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select_filter, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString(EXTRA_FILTER)?.let {
            filterBy = it
        }
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)

        setupAdapter()

        setupFilter()
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    private fun setupFilter() {
        filter_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter?.filter(filter_text.text.toString())
            }
        })

        filter_clear.setOnClickListener {
            filter_text.setText("")
        }

        filter_dismiss.setOnClickListener{
            goBack()
        }

        filter_area.setOnClickListener{
            goBack()
        }

        filter_refresh.visibility = if(filterBy == EXTRA_CALIBERS){
            View.VISIBLE
        } else {
            View.INVISIBLE
        }

        filter_refresh.setOnClickListener {
            filter_refresh.visibility = View.INVISIBLE
            GlobalScope.launch {
                viewModel.reset(filter_refresh.context)
                withContext(Dispatchers.Main){
                    adapter!!.notifyDataSetChanged()
                    viewModel.query.filters.clear()
                    filter_refresh.visibility = View.VISIBLE

                }
            }
        }
    }

    private fun goBack() {
        fragmentManager!!.popBackStack()
        EventBus.getDefault().post(FilterDialogClosed(filterBy))
    }

    private fun setupAdapter() {
        if(adapter == null) {
            val context = filter_clear.context

            manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            filter_recycler.layoutManager = manager

            val filters: ArrayList<Filter>
            val prefName: String?
            val title: Int
            when (filterBy) {
                EXTRA_CATEGORIES -> {
                    filters = filterDB.categories
                    prefName = PrefUtils.PREF_CATEGORY
                    title = R.string.categories_title
                }
                EXTRA_CALIBERS -> {
                    filters = filterDB.calibers
                    prefName = null
                    title = R.string.caliber_title
                }
                EXTRA_ACTIONS -> {
                    filters = filterDB.actions
                    prefName = PrefUtils.PREF_ACTION_TYPE
                    title = R.string.firearm_type_title
                }
                else -> {
                    filters = filterDB.firearmTypes
                    prefName = PrefUtils.PREF_FIREARM_TYPE
                    title = R.string.action_type_title
                }
            }

            filter_title.setText(title)

            adapter = FilterAdapter(context, filters, prefName)

            filter_recycler.adapter = adapter
        }
    }

    @Subscribe
    fun onFilterChange(filter : Filter) {
        GlobalScope.launch {
            val prefs = context?.getSharedPreferences(PrefUtils.PREFERENCE_FIELD, Context.MODE_PRIVATE)!!
            if(prefs.getBoolean(filter.value, false)) {
                viewModel.query.filters[filter.value] = filter
            } else {
                viewModel.query.filters.remove(filter.value)
            }
        }
    }

}