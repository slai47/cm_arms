package com.slai.cmarms.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.slai.cmarms.R
import com.slai.cmarms.Utils
import com.slai.cmarms.model.Filter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class FilterAdapter(val context: Context, val array : ArrayList<Filter>) : RecyclerView.Adapter<FilterViewHolder>() {

    constructor(context: Context, array: ArrayList<Filter>, prefName : String?) : this(context, array) {
        if(prefName != null) {
            singleOnly = true
            storedPrefName = prefName
            selectedValue = prefs.getString(prefName, "")!!
            if (selectedValue.isNotEmpty()) {
                var i = 0
                for (filter in array) {
                    if (selectedValue == filter.value) {
                        break
                    }
                    i++
                }
                previousSelection = i
            }
        }
    }

    var singleOnly = false
    var storedPrefName = ""

    val prefs : SharedPreferences = context.getSharedPreferences(Utils.PREFERENCE_FIELD, Context.MODE_PRIVATE)

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
        holder.itemView.tag = position

        holder.itemView.setOnClickListener {
            val pos = it.tag as Int
            val filter = array[pos]
            val preference = if(!singleOnly) filter.value
                                    else storedPrefName
            if(singleOnly) {
                val value = prefs.getString(preference,"")
                if(value != filter.value) {
                    prefs.edit().putString(preference, filter.value).apply()
                    selectedValue = filter.value
                    GlobalScope.launch(Dispatchers.Main) {
                        notifyItemChanged(previousSelection)
                        notifyItemChanged(pos)
                        previousSelection = pos
                    }
                } else {
                    prefs.edit().putString(preference, "").apply()
                    holder.check.isChecked = false
                    selectedValue = ""
                    previousSelection = -1
                }
            } else {
                var value = prefs.getBoolean(preference,false)
                value = !value
                holder.check.isChecked = value
                prefs.edit().putBoolean(preference, value).apply()
            }
            EventBus.getDefault().post(filter)
        }
    }
}

class FilterViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val check = view.findViewById<CheckBox>(R.id.list_checkbox)
}