package com.slai.cmarms.presenters

import com.slai.cmarms.FeedFragment
import com.slai.cmarms.interfaces.IArmsService
import com.slai.cmarms.interfaces.IPresenter
import com.slai.cmarms.model.Query
import kotlinx.coroutines.*

class FeedPresenter(val feedFragment: FeedFragment) : IPresenter {

    val job = Job()
    val scope = CoroutineScope(Dispatchers.IO + job)

    fun searchForPosts(query : Query) {
        val api = IArmsService.getService()
        // launch coroutines
        scope.async {
            val list = api.getPosts(query)
            withContext(Dispatchers.Main) {
                feedFragment.onPostsReceived(list)
            }
        }
    }

    override fun dispose() {
        job.cancel()
    }
}
