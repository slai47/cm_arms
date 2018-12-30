package com.slai.cmarms.view

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slai.cmarms.R
import com.slai.cmarms.adapters.FilterAdapter

class DialogRecyclerView(context: Context) : LinearLayout(context) {

    var manager : LinearLayoutManager
    var recycler : RecyclerView

    private lateinit var adapter : FilterAdapter

    init {
        View.inflate(context, R.layout.dialog_recycler, this)
        recycler = findViewById(R.id.dialog_recycler)

        manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recycler.layoutManager = manager
    }

    fun setAdapter(filter : FilterAdapter) {
        adapter = filter
        recycler.adapter = adapter
    }
}