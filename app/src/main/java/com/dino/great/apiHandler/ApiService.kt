package com.dino.great.apiHandler

import com.dino.great.BuildConfig
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    /* creating a singleton object for retrofit client */
    companion object {
        val instance: ApiService by lazy {
            ApiFactory.retrofit(BuildConfig.BASEURL).create(ApiService::class.java)
        }
    }

    /**
     * Employee List*/
//    @GET("5d565297300000680030a986")
//    suspend fun getEmployeeList(): Response<List<Employee>?>
}