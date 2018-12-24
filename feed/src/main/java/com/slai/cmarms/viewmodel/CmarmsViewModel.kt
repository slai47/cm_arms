package com.slai.cmarms.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slai.cmarms.model.Post
import com.slai.cmarms.model.Query

class CmarmsViewModel : ViewModel() {

    private lateinit var posts : MutableLiveData<ArrayList<Post>>
    val query by lazy { Query() }

    fun getPosts() : LiveData<ArrayList<Post>> {
        if(!::posts.isInitialized){
            posts = MutableLiveData()
            posts.value = ArrayList()
        }
        return posts
    }

    fun addPosts(newPosts : List<Post>) {
        if(::posts.isInitialized){
           posts.value?.addAll(newPosts)
            // doing this for force the viewmodel to alert all observers of changes
            posts.value = posts.value
        }
    }

}