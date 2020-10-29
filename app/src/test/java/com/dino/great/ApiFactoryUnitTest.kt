package com.dino.great

import com.dino.great.apiHandler.ApiFactory
import org.junit.Test

import org.junit.Assert.*

class ApiFactoryUnitTest {
    @Test
    fun testApiFactory(){
        val apiFactory = ApiFactory.retrofit("https://jsonplaceholder.typicode.com/")
        assertNotNull(apiFactory)
    }
}