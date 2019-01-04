package com.slai.cmarms.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.slai.cmarms.interfaces.PostDao
import com.slai.cmarms.model.Post
import com.slai.cmarms.model.converters.Converters

@Database(entities = arrayOf(Post::class), version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao() : PostDao
}