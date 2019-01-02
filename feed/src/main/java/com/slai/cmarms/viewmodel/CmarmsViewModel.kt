package com.slai.cmarms.viewmodel

import android.app.Application
import android.content.Context
import android.provider.Settings
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.slai.cmarms.db.AppDatabase
import com.slai.cmarms.model.Post
import com.slai.cmarms.model.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CmarmsViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var livePosts : LiveData<MutableList<Post>>
    val query = Query()
    var endOfQueue = false
    var appDatabase: AppDatabase =
        Room.inMemoryDatabaseBuilder(application.applicationContext, AppDatabase::class.java).build()

    fun getLivePosts() : LiveData<MutableList<Post>> {
        if(!::livePosts.isInitialized){
            livePosts = MutableLiveData()
            livePosts = appDatabase.postDao().loadLivePosts()
        }
        return livePosts
    }

    fun addPosts( newPosts : ArrayList<Post>) {
        GlobalScope.launch( Dispatchers.IO) {
            for(post in newPosts) {
                appDatabase.postDao().insertPost(post)
            }
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
        GlobalScope.launch(Dispatchers.IO) {
            appDatabase.postDao().nukePosts()
        }
        livePosts.value!!.clear()
        endOfQueue = false
        query.page = 0
    }
}