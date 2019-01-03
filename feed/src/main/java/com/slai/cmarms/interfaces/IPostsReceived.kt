package com.slai.cmarms.interfaces

import com.slai.cmarms.model.Post

interface IPostsReceived {

    fun onPostsReceived(posts : ArrayList<Post>)

}