package com.slai.cmarms.interfaces

import com.slai.cmarms.backend.ArmsAPI
import com.slai.cmarms.model.Post
import com.slai.cmarms.model.Query

interface IArmsService {

    suspend fun getPosts(query : Query) : List<Post>


    companion object {
        fun getService() : IArmsService {
            return ArmsAPI()
        }
    }
}
