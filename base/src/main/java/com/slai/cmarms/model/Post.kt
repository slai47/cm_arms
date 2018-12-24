package com.slai.cmarms.model

data class Post(val title : String) {

    var image : String = ""
    var description : String = ""
    var url : String = ""
    var price : String = ""
    var type : SaleType = SaleType.SALE
    var location : String = ""

}

enum class SaleType {
    TRADE,
    SALE,
    BOTH
}