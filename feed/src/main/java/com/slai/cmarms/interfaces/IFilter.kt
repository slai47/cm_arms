package com.slai.cmarms.interfaces

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

interface IFilter {

    fun getFragmentManager() : FragmentManager?

    fun updateLocationButton(on : Boolean)

    fun setLocation(state : String)

    fun showSnackbar(message : String)

    fun getString(resId : Int) : String

    fun setViewModelItems()

    fun searchReset()

    fun getFragmentActivity() : FragmentActivity
}