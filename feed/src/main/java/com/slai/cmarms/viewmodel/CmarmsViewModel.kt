package com.slai.cmarms.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slai.cmarms.model.Post
import com.slai.cmarms.model.Query

class CmarmsViewModel : ViewModel() {

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

    suspend fun reset(context : Context){
        if(::livePosts.isInitialized){
            livePosts.value = ArrayList()
        }
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
        livePosts.value?.clear()
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