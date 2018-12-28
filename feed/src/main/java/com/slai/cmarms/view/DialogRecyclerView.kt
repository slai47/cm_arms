package com.slai.cmarms.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slai.cmarms.R
import com.slai.cmarms.adapters.FilterAdapter

class DialogRecyclerView : View {

    lateinit var manager : LinearLayoutManager
    lateinit var recycler : RecyclerView

    private lateinit var adapter : FilterAdapter

    constructor(context: Context) : this(context, null) {
        inflate(context, R.layout.dialog_recycler, null)
        recycler = findViewById(R.id.dialog_recycler)
        init(context)
    }

    constructor(context: Context, filter : FilterAdapter) : super(context) {
        setAdapter(filter)
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    private fun init(context : Context) {
        manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recycler.layoutManager = manager
    }

    fun setAdapter(filter : FilterAdapter) {
        adapter = filter
        recycler.adapter = adapter
    }
}