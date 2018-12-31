package com.slai.cmarms.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Post(var title : String) {

    @PrimaryKey
    var id : Long = 0L
    var image : String = ""
    var description : String = ""
    var url : String = ""
    var price : String = ""
    var location : String = ""

}