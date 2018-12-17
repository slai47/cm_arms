package com.slai.cmarms.model

data class Post(val title : String, val image : String, val description : String, val url : String, val price : String, val type : SaleType)

enum class SaleType {
    TRADE,
    SALE,
    BOTH
}