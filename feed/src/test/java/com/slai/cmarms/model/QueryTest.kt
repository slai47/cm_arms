package com.slai.cmarms.model

import org.junit.Assert
import org.junit.Test

class QueryTest {

    val postType = "posttype=7"
    val locationD = "location=usa"
    val categoryD = "category=guns"
    val query = Query()

    @Test
    fun emptyTest(){
        Assert.assertTrue(query.getURLExtras() == "?$locationD&$categoryD&$postType")
    }

    @Test
    fun locationTest(){
        query.location = "Utah"
        Assert.assertTrue(query.getURLExtras() == "?location=Utah&$categoryD&$postType")
    }

    @Test
    fun categoryTest(){
        query.reset()
        query.category = "Knives"
        Assert.assertTrue(query.getURLExtras() == "?$locationD&category=Knives&$postType")
    }

    @Test
    fun pageTest(){
        query.reset()
        query.page = 2
        Assert.assertTrue(query.getURLExtras() == "?$locationD&page=2&$categoryD&$postType")
    }

    @Test
    fun filtersTest(){
        query.reset()
        query.filters.put("caliber", Filter(0, "223/556"))
        Assert.assertTrue(query.getURLExtras() == "?$locationD&$categoryD&$postType&tag=223/556")
    }

    @Test
    fun lowPriceTest(){
        query.reset()
        query.lowPrice = 500
        Assert.assertTrue(query.getURLExtras() == "?$locationD&$categoryD&startPrice=500&$postType")
    }

    @Test
    fun highPriceTest(){
        query.reset()
        query.highPrice = 500
        Assert.assertTrue(query.getURLExtras() == "?$locationD&$categoryD&endprice=500&$postType")
    }
}