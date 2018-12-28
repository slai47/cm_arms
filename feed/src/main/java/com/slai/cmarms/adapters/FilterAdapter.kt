package com.slai.cmarms.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.slai.cmarms.R
import com.slai.cmarms.model.Filter
import org.greenrobot.eventbus.EventBus

class FilterAdapter(val context: Context, val array : ArrayList<Filter>) : RecyclerView.Adapter<FilterViewHolder>() {

    constructor(context: Context, array: ArrayList<Filter>, singular: Boolean, prefName : String) : this(context, array) {
        singleOnly = singular
        storedPrefName = prefName
    }

    var singleOnly = false
    var storedPrefName = ""

    val prefs : SharedPreferences = context.getSharedPreferences("cmarms", Context.MODE_PRIVATE)

    var selectedValue = ""
    var previousSelection = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        //setup different ones depending on type
        return FilterViewHolder(LayoutInflater.from(context).inflate(R.layout.list_check, parent, false))
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filter = array[position]
        holder.check.setText(filter.titleId)
        holder.check.isChecked =
                if(!singleOnly) prefs.getBoolean(filter.value, false)
                else filter.value == selectedValue
        holder.check.tag = position
        holder.check.setOnCheckedChangeListener { buttonView, isChecked ->
            val pos = buttonView.tag as Int
            val filter = array[pos]
            if(singleOnly) {
                prefs.edit().putBoolean(filter.value, isChecked).apply()
                EventBus.getDefault().post(filter)
            } else {
                selectedValue = filter.value
                prefs.edit().putString(storedPrefName, filter.value).apply()
                notifyItemChanged(previousSelection)
                notifyItemChanged(pos)
                previousSelection = pos
            }
        }
    }
}

class FilterViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val check = view.findViewById<CheckBox>(R.id.list_checkbox)
}