package com.slai.cmarms.model


data class Action(val titleId : Int, val value : String)

data class FirearmType(val titleId : Int, val value : String)

data class Caliber(val titleId : Int, val value : String)

data class Manufacturer(val titleId : Int, val value : String)

data class Category(val titleId : Int, val value : String, val group : Boolean) {

    constructor(titleId: Int, value: String) : this(titleId, value, false)
}

