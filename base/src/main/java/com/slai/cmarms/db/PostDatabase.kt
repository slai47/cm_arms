package com.slai.cmarms.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.slai.cmarms.interfaces.PostDao
import com.slai.cmarms.model.Post

@Database(entities = arrayOf(Post::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao() : PostDao
}