package com.slai.cmarms.model

data class Query(var search : String = "") {

    var location : String = ""
    var category : String = ""
    var page : Int = 0


    fun getURLExtras() : String {
        val builder = StringBuilder()

        builder.append("search=$search")

        builder.append("&location=$location")

        if(page > 0) builder.append("&page=$page")

        if(category.isNotEmpty()) builder.append("&category=$category")

        return builder.toString()
    }
}