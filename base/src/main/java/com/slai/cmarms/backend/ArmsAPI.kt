package com.slai.cmarms.backend

import android.util.Log
import com.slai.cmarms.interfaces.IArmsService
import com.slai.cmarms.model.Post
import com.slai.cmarms.model.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import okhttp3.OkHttpClient
import okhttp3.Request

class ArmsAPI {

    val baseURL = "http://www.armslist.com/feed.rss?"

    val job : Job = Job()
    val scope = CoroutineScope(Dispatchers.Default + job)

    fun getPosts(query : Query) : List<Post>{
        // get RSS feed
        val queryItems = query.getURLExtras()
        val url = "$baseURL?$queryItems"

        // Call for feed
        val client = OkHttpClient.Builder().build()
        val request : Request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        val feed = response.body()?.string()

        var list : List<Post> = ArrayList()
        // parse RSS Feed
        if(feed.isNullOrEmpty())
            list = parsePosts(feed!!, list)
        return list
    }

    fun parsePosts(feed : String, list : List<Post>) : List<Post> {
        Log.d(ArmsAPI::class.java.name, feed)

        return list
    }
}