package com.slai.cmarms.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.slai.cmarms.model.Post

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPost(vararg post : Post)

    @Delete
    fun removePost(vararg post : Post)

    @Query("SELECT * FROM posts")
    fun loadPosts() : List<Post>

    @Query("SELECT * FROM posts")
    fun loadLivePosts() : LiveData<MutableList<Post>>

    @Query("SELECT * FROM posts WHERE id = :id")
    fun loadPost(id: Long) : Post

    @Query("DELETE FROM posts")
    fun nukePosts()
}