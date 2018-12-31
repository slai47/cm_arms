package com.slai.cmarms.presenters

import android.app.AlertDialog
import android.content.Context
import android.widget.Button
import com.slai.cmarms.FilterFragment
import com.slai.cmarms.FiltersDataHolder
import com.slai.cmarms.R
import com.slai.cmarms.Utils
import com.slai.cmarms.adapters.FilterAdapter
import com.slai.cmarms.interfaces.IPresenter
import com.slai.cmarms.view.DialogRecyclerView

class FilterPresenter(val fragment : FilterFragment) : IPresenter {

    private val filterDB = FiltersDataHolder()

    fun setupDialogs(context : Context, category : Button, caliber : Button, firearmType : Button, actionType : Button) {
        category.setOnClickListener {
            val adapter = FilterAdapter(context, filterDB.categories, Utils.PREF_CATEGORY)
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.categories_title))
            val recyclerView = DialogRecyclerView(context)
            recyclerView.setAdapter(adapter)
            builder.setView(recyclerView)
            builder.setPositiveButton(context.getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
            }
            builder.setOnDismissListener {
                fragment.updateButtonText()
            }
            builder.show()
        }
        caliber.setOnClickListener {
            val adapter = FilterAdapter(context, filterDB.calibers)
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.caliber_title))
            val recyclerView = DialogRecyclerView(context)
            recyclerView.setAdapter(adapter)
            builder.setView(recyclerView)
            builder.setPositiveButton(context.getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
            }
            builder.setOnDismissListener {
                fragment.updateButtonText()
            }
            builder.show()
        }
        firearmType.setOnClickListener {
            val adapter = FilterAdapter(context, filterDB.firearmTypes, Utils.PREF_FIREARM_TYPE)
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.firearm_type_title))
            val recyclerView = DialogRecyclerView(context)
            recyclerView.setAdapter(adapter)
            builder.setView(recyclerView)
            builder.setPositiveButton(context.getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
            }
            builder.setOnDismissListener {
                fragment.updateButtonText()
            }
            builder.show()
        }
        actionType.setOnClickListener {
            val adapter = FilterAdapter(context, filterDB.actions, Utils.PREF_ACTION_TYPE)
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.action_type_title))
            val recyclerView = DialogRecyclerView(context)
            recyclerView.setAdapter(adapter)
            builder.setView(recyclerView)
            builder.setPositiveButton(context.getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
            }
            builder.setOnDismissListener {
                fragment.updateButtonText()
            }
            builder.show()
        }
    }

    override fun dispose() {

    }

}