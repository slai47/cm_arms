package com.slai.cmarms.model

data class Query(var search : String = "") {

    var location : String = "usa"
    var category : String = "guns"
    var page : Int = 1
    var currency : String = ""

    var filters = HashMap<String, Filter>()

    var lowPrice : Int = 0
    var highPrice : Int = 0

    var sellerType : Int = -1

    fun getURLExtras() : String {
        val builder = StringBuilder()

        builder.append("?")
        builder.append("location=$location")

        if(search.isNotEmpty()) {
            val s = search.replace(" ", "+")
            builder.append("&search=$s")
        }

        if(page > 1) builder.append("&page=$page")

        if(category.isNotEmpty()) builder.append("&category=$category")

        if(currency.isNotEmpty()) builder.append("&currency=$currency")

        if(sellerType > 0) builder.append("&sellerType=$sellerType")

        if(lowPrice > 0) builder.append("&startprice=$lowPrice")

        if(highPrice > 0) builder.append("&endprice=$highPrice")

        if(filters.isNotEmpty()) {
            for ((title, va) in filters) {
                builder.append("&tag=$va")
            }
        }
        builder.append("&posttype=7")
        return builder.toString()
    }

    fun reset() {
        location = "usa"
        category = "guns"
        page = 1
        currency = ""
        filters.clear()
        lowPrice = 0
        highPrice = 0
        sellerType = -1

    }
}