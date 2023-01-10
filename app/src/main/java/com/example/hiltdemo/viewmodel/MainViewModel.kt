package com.example.hiltdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltdemo.models.Products
import com.example.hiltdemo.repository.MainRepository
import com.example.hiltdemo.states.ProductState
import com.example.hiltdemo.utils.ResultHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private  val repository: MainRepository) :ViewModel(){

    /* val productsLiveData: LiveData<List<Products>>
    get() = repository.productList*/

    private val _productState=MutableStateFlow<ProductState>(ProductState.Idle)
    val productState=_productState.asStateFlow()

    init {
        getProductList()
    }

     fun getProductList() {
        _productState.value=ProductState.ShowLoading
        viewModelScope.launch {
            when(val res = repository.getProducts()){
                is ResultHandler.Error -> {
                    _productState.value=ProductState.Error(res.throwable)
                }
                is ResultHandler.Success -> {
                    val response= (res.data as Response<*>)
                    if(response.isSuccessful){
                        val data= res.data.body()  as List<Products>
                        _productState.value =ProductState.Success(data)
                    }else{
                        _productState.value= ProductState.Error(Throwable(("Something went wrong.")))
                    }

                }
            }
        }

    }

}