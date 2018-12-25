package com.slai.cmarms.model

data class Query(var search : String = "") {

    var location : String = "usa"
    var category : String = "all"
    var page : Int = 1


    fun getURLExtras() : String {
        val builder = StringBuilder()

        builder.append("?")
        builder.append("location=$location")

        if(search.isNotEmpty())
            builder.append("&search=$search")

        if(page > 1) builder.append("&page=$page")

        if(category.isNotEmpty()) builder.append("&category=$category")

        return builder.toString()
    }
}