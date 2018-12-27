package com.slai.cmarms

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.slai.cmarms.adapters.FilterAdapter
import com.slai.cmarms.model.Filter
import com.slai.cmarms.viewmodel.CmarmsViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FilterFragment : Fragment() {

    lateinit var viewModel : CmarmsViewModel

    lateinit var adapter : FilterAdapter
    lateinit var manager : GridLayoutManager

    val filterDB = FiltersDataHolder()

    val prefs = context?.getSharedPreferences("cmarms", Context.MODE_PRIVATE)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
        viewModel = ViewModelProviders.of(this).get(CmarmsViewModel::class.java)

        // setup adapter
        filter_list.apply {
            manager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

            adapter = FilterAdapter(filter_list.context, filterDB.calibers)

            filter_list.layoutManager = manager
            filter_list.adapter = adapter
        }
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onFilterChange(filter : Filter) {
        Snackbar.make(filter_list, "${filter.value} was changed", Snackbar.LENGTH_SHORT)
        GlobalScope.launch {
            if(prefs!!.getBoolean("", false)){
                viewModel.query.filters[filter.value] = filter
            } else {
                viewModel.query.filters.remove(filter.value)
            }
        }
    }
}