package com.slai.cmarms.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slai.cmarms.view.SearchTextView

class FilterAdapter : RecyclerView.Adapter<FilterViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        //setup different ones depending on type
        return FilterViewHolder(SearchTextView(parent.context))
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {

    }
}

class FilterViewHolder(view : View) : RecyclerView.ViewHolder(view) {

}