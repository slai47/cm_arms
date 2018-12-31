package com.slai.cmarms.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.slai.cmarms.model.Post
import com.slai.cmarms.model.Query

class CmarmsViewModel {

    private lateinit var posts : ArrayList<Post> // adding this to allow use of MVVM and prevent dups
    private lateinit var livePosts : MutableLiveData<ArrayList<Post>>
    val query by lazy { Query() }
    var endOfQueue = false


    fun getLivePosts() : LiveData<ArrayList<Post>> {
        if(!::livePosts.isInitialized){
            livePosts = MutableLiveData()
            livePosts.value = ArrayList()
            posts = ArrayList()
        }
        return livePosts
    }

    fun getPosts() : ArrayList<Post> {
        getLivePosts()
        return posts
    }

    fun addPosts(newPosts : ArrayList<Post>) {
        if(::livePosts.isInitialized){
            posts.addAll(newPosts)
            livePosts.value = newPosts
        }
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
        posts.clear()
        if(::livePosts.isInitialized){
            livePosts.value = ArrayList()
        }
        endOfQueue = false
    }

    fun getPostIds() : ArrayList<Long> {
        val list = ArrayList<Long>()
        for(post in posts){
            list.add(post.id)
        }
        return list
    }
}