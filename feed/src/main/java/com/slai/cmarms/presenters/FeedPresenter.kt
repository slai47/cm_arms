package com.slai.cmarms.presenters

import android.util.Log
import com.slai.cmarms.FeedFragment
import com.slai.cmarms.interfaces.IArmsService
import com.slai.cmarms.interfaces.IPresenter
import com.slai.cmarms.model.Post
import com.slai.cmarms.model.Query
import kotlinx.coroutines.*
import me.toptas.rssconverter.RssFeed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import me.toptas.rssconverter.RssItem


class FeedPresenter(val feedFragment: FeedFragment) : IPresenter {

    val job = Job()
    val scope = CoroutineScope(Dispatchers.IO + job)

    fun searchForPosts(
        query: Query
    ) {
        val api = IArmsService.getService()
        // launch coroutines
        scope.launch {
            val url = "http://www.armslist.com/feed.rss/" + query.getURLExtras()
            Log.d("FeedPresenter", "$url")
            val request = api.getPosts(url)
            request.enqueue(object : Callback<RssFeed> {
                override fun onFailure(call: Call<RssFeed>, t: Throwable) {
                    scope.launch {
                        feedFragment.onPostsReceived(ArrayList())
                    }
                }

                override fun onResponse(call: Call<RssFeed>, response: Response<RssFeed>) {
                    val list = ArrayList<Post>()
                    if(response.isSuccessful) {
                        for (it in response.body()?.items!!) {
                            Log.d(FeedPresenter::class.java.simpleName,"${it.title}")
                            val post = parsePost(it)
                            list.add(post)
                        }
                    }
                    scope.launch(Dispatchers.Main) {
                        feedFragment.onPostsReceived(list)
                    }
                }
            })
        }
    }

    private fun parsePost(it: RssItem): Post {
        val fullTitle = it.title.toString()
        var titleEndIndex = fullTitle.indexOf("$")
        if (titleEndIndex < 0) // check if now amount is there and its an offer
            titleEndIndex = fullTitle.indexOf("- Offer") - 1
        if(titleEndIndex < 0) // check if the offer isn't there.
            titleEndIndex = fullTitle.length


        val simpleTitle = fullTitle.substring(fullTitle.indexOf(")") + 1, titleEndIndex - 1).trim().replace("-", "")

        val post = Post(simpleTitle)
        post.description = it.description.toString()
        post.image = it.image.toString()
        post.url = it.link.toString()
        post.location = fullTitle.substring(fullTitle.indexOf("(") + 1, fullTitle.indexOf(")")).trim()
        post.price = fullTitle.substring(titleEndIndex + 1, fullTitle.length).trim()

        if(post.price.contains("offer", false))
            post.price = "Offer"

        if(post.url.isNotEmpty()){
            post.id = post.url.replace("http://www.armslist.com/posts/", "").toLong()
        }

        return post
    }

    override fun dispose() {
        job.cancel()
    }
}
