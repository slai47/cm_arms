package com.slai.cmarms.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.slai.cmarms.R

class SearchTextView : View {

    private lateinit var text : TextView

    constructor(context: Context) : this(context, null) {
        inflate(context, R.layout.view_search_text, null)
        text = findViewById(R.id.search_et)
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    fun getText() : String {
        return text?.text.toString()
    }
}