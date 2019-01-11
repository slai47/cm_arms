package com.slai.cmarms.backend

import android.util.Log
import com.slai.cmarms.interfaces.IPostsReceived
import com.slai.cmarms.model.Post
import com.slai.cmarms.model.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class PostApi() {

    val TAG = PostApi::class.java.simpleName
    val job = Job()
    val scope = CoroutineScope(Dispatchers.IO + job)

    fun searchForPosts( callback : IPostsReceived,
        query: Query
    ) : Job{
        // launch coroutines
        return scope.launch {
            val url = "http://www.armslist.com/classifieds/search" + query.getURLExtras()
            Log.d(TAG, "$url")
            val doc = Jsoup.connect(url).get()
            val images  = doc.select(".col-md-5")
            val texts = doc.select(".col-md-7")
            Log.d(TAG, "${images.size}")
            Log.d(TAG, "${texts.size}")

            val imageList = getImageList(images)

            val posts = parseData(texts, imageList)

            scope.launch(Dispatchers.Main) {
                callback.onPostsReceived(posts)
            }
        }
    }

    private fun parseData(
        texts: Elements,
        imageList: ArrayList<String>
    ): ArrayList<Post> {
        val posts = ArrayList<Post>()
        var i = 0
        texts.forEach { text ->
            // grab name
            var name = ""
            // grab price
            val pair = parseCostName(text)
            val cost = pair.first
            name = pair.second

            val post = Post(name.trim())
            post.price = cost
            post.image = imageList[i]

            // grab url
            parseUrlId(text, post)

            parseLocationTimeSaleType(text, post)

            posts.add(post)
            i++
        }
        return posts
    }


    private fun parseLocationTimeSaleType(text: Element, post: Post) {
        // grab location
        val locationHtml = text.select("small")
        post.premium = locationHtml.text().contains("Premium Vendor", true)
        var startIndex = 0
        if(post.premium) startIndex++

        Log.d(TAG, "Parse Cost = ${locationHtml.text()}")
        try {
            val saleType = locationHtml[startIndex]
            post.saleType = saleType.text()
        } finally {
        }
        try {
            val location = locationHtml[startIndex + 1]
            post.location = location.text()
        } finally {
        }
        try {
            val time = locationHtml[startIndex + 2]
            post.time = time.text()
        } finally {
        }
    }

    private fun parseUrlId(text: Element, post: Post) {
        var url = ""
        val links = text.select("a")
        links.forEach {
            val href = it.attr("href")
            if (href.startsWith("/posts"))
                url = href
        }
        post.url = url
        // pull out id
        try {
            if (url.isNotEmpty()) {
                val idSplit = url.split("/")
                if (idSplit.size > 2)
                    post.id = idSplit.get(2).toLong()
            }
        } finally {
        }
    }

    private fun parseCostName(
        text: Element
    ): Pair<String, String> {
        var name1 = ""
        var costSplit = text.text().split("$")
        if (costSplit.size <= 1) {
            costSplit = text.text().split("Offer")
        }
        var cost = "Offer"
        if (costSplit.size > 1) {
            name1 = costSplit[0]
            val splitAgain = costSplit[1].trim().split(" ")
            cost = splitAgain[0]
            if(cost == "For")
                cost = "Offer"
        }
        Log.d(PostApi::class.java.simpleName, "Parse Cost = ${text.text()}")
        return Pair(cost, name1)
    }

    private fun getImageList(images: Elements): ArrayList<String> {
        val imageList = ArrayList<String>()

        images.forEach { image ->
            val a = image.select(".img-responsive")
            if (a != null && a.size > 0)
                imageList.add(a[0].attr("src"))
        }
        return imageList
    }

}