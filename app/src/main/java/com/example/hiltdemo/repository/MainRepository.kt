package com.example.hiltdemo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hiltdemo.models.Products
import com.example.hiltdemo.network.ApiService
import com.example.hiltdemo.utils.ResultHandler
import com.example.hiltdemo.utils.runIO
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {
    private val _productList=MutableLiveData<List<Products>>()
    val productList:LiveData<List<Products>>
    get() = _productList

    suspend fun getProducts(): ResultHandler<Any> {
       return runIO { apiService.getProducts() }
    }
}


