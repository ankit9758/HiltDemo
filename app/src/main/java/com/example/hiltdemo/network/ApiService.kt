package com.example.hiltdemo.network

import com.example.hiltdemo.models.Products
import com.example.hiltdemo.utils.AppConstants
import retrofit2.Response
import retrofit2.http.GET
interface ApiService {

    @GET(AppConstants.PRODUCT_LIST)
    suspend fun getProducts(): Response<List<Products>>
}