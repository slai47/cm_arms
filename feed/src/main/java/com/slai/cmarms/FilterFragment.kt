package com.slai.cmarms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slai.cmarms.adapters.FilterAdapter
import com.slai.cmarms.viewmodel.CmarmsViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class FilterFragment : Fragment() {

    lateinit var viewModel : CmarmsViewModel

    lateinit var adapter : FilterAdapter
    lateinit var manager : GridLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onResume() {
        super.onResume()
        viewModel = ViewModelProviders.of(this).get(CmarmsViewModel::class.java)

        // setup adapter
        filter_list.apply {
            manager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

            adapter = FilterAdapter()


            filter_list.layoutManager = manager
            filter_list.adapter = adapter
        }
    }

    override fun onPause() {
        super.onPause()
    }
}