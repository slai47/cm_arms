package com.slai.cmarms.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slai.cmarms.view.SearchTextView

class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        //setup different ones depending on type
        return SearchViewHolder(SearchTextView(parent.context))
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

    }

}

class SearchViewHolder(view : View) : RecyclerView.ViewHolder(view) {

}