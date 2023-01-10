package com.example.hiltdemo.states

import com.example.hiltdemo.models.Products

sealed class ProductState{
    object ShowLoading:ProductState()
    object Idle:ProductState()
    class Success(val data: List<Products>) : ProductState()
    class Error(val throwable: Throwable) : ProductState()
}
