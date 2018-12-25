package com.slai.cmarms.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slai.cmarms.model.Post
import com.slai.cmarms.model.Query

class CmarmsViewModel : ViewModel() {

    private lateinit var posts : ArrayList<Post> // adding this to allow use of MVVM and prevent dups
    private lateinit var livePosts : MutableLiveData<ArrayList<Post>>
    val query by lazy { Query() }

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

    fun reset(){
        if(::livePosts.isInitialized){
            livePosts.value = ArrayList()
        }
    }

}