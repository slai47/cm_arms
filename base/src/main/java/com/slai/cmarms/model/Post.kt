package com.slai.cmarms.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "posts")
data class Post(var title : String) {

    @PrimaryKey
    var id : Long = 0L
    var image : String = ""
    var url : String = ""
    var price : String = ""
    var location : String = ""
    var saleType : String = ""
    var time : String = ""
    var createdTime = Date()
}