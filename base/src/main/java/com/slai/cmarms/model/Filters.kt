package com.slai.cmarms.model

class Filter(val titleId : Int, val value : String, val group : Boolean) {
    constructor(titleId: Int, value: String) : this(titleId, value, false)
}

