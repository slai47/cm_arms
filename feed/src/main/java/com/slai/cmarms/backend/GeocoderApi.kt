package com.slai.cmarms.backend

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import com.slai.cmarms.interfaces.IDispose
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class GeocoderApi(val context : Context) : IDispose {

    val TAG = PostApi::class.java.simpleName
    val job = Job()
    val scope = CoroutineScope(Dispatchers.IO + job)

    fun getAddress(location : Location, callback : (Address?) -> Unit) {
        scope.launch {
            val geocoder = Geocoder(context, Locale.getDefault())
            var addresses = emptyList<Address>()
            try {
                addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            } catch (e: Exception) {

            } finally {

            }
            var address : Address? = null
            if(addresses.isNotEmpty()){
                address = addresses[0]
            }
            scope.launch(Dispatchers.Main) {
                callback(address)
            }
        }
    }

    override fun dispose() {
        job.cancel()
    }
}