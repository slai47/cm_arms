package com.slai.cmarms.model

data class Category(val resId : Int, val value : String, val group : Boolean) {
    companion object {

        lateinit var categories : ArrayList<Category>

        fun getCategories() : List<Category> {
            if(!::categories.isInitialized) {
                categories = ArrayList()

                

            }
            return categories
        }
    }
}