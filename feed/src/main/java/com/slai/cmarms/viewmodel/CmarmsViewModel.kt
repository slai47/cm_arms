package com.slai.cmarms.viewmodel

import android.app.Application
import android.content.Context
import android.provider.Settings
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.bumptech.glide.Glide
import com.slai.cmarms.backend.PostApi
import com.slai.cmarms.db.AppDatabase
import com.slai.cmarms.interfaces.IDispose
import com.slai.cmarms.interfaces.IPostsReceived
import com.slai.cmarms.model.Post
import com.slai.cmarms.model.ProgressEvent
import com.slai.cmarms.model.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class CmarmsViewModel(application: Application) : AndroidViewModel(application), IPostsReceived, IDispose {



    private lateinit var livePosts : LiveData<MutableList<Post>>
    val query = Query()
    var endOfQueue = false
    var appDatabase: AppDatabase =
        Room.inMemoryDatabaseBuilder(application.applicationContext, AppDatabase::class.java).build()

    private val postApi = PostApi(this)
    private var job : Job? = null

    fun getLivePosts() : LiveData<MutableList<Post>> {
        if(!::livePosts.isInitialized){
            livePosts = MutableLiveData()
            livePosts = appDatabase.postDao().loadLivePosts()
            postApi.searchForPosts(query)
            EventBus.getDefault().post(ProgressEvent(true))
        }
        return livePosts
    }

    fun getPosts(){
        postApi.searchForPosts(query)
    }

    /**
     * This is reseting of the prefences
     */
    suspend fun reset(context : Context){
        val pref = context.getSharedPreferences("cmarms", Context.MODE_PRIVATE)
        val edit = pref.edit()
        for((title, filter) in query.filters){
            edit.putBoolean(filter.value, false)
        }
        edit.apply()
        query.reset()
    }

    fun clearPosts() {
        GlobalScope.launch(Dispatchers.IO) {
            appDatabase.postDao().nukePosts()
        }
        livePosts.value!!.clear()
        endOfQueue = false
        query.page = 0
    }

    override fun onPostsReceived(posts: ArrayList<Post>) {
        when {
            posts.isNotEmpty() -> GlobalScope.launch( Dispatchers.IO) {
                for(post in posts) {
                    appDatabase.postDao().insertPost(post)
                }
            }
            livePosts.value!!.isNotEmpty() -> {
                endOfQueue = true
                job?.cancel()
            }
            else -> {
                // no searches found
            }
        }
        EventBus.getDefault().post(ProgressEvent(false))
        job = null
    }

    override fun dispose() {
        job?.cancel()
    }
}