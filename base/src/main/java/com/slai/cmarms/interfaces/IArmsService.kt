package com.slai.cmarms.interfaces

import me.toptas.rssconverter.RssConverterFactory
import me.toptas.rssconverter.RssFeed
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url


interface IArmsService {

    @GET
    fun getPosts(@Url query : String) : Call<RssFeed>


    companion object {
        fun getService() : IArmsService {
            val builder = Retrofit.Builder()
                .baseUrl("http://www.armslist.com/feed.rss/")
                .addConverterFactory(RssConverterFactory.create())
                .build()
            return builder.create(IArmsService::class.java)
        }
    }
}
