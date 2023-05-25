package com.example.loginapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.loginapp.Api.ApiService
import com.example.loginapp.models.Categories
import com.example.loginapp.models.ProductInfo

class ProductRepository(private val apiService: ApiService) {
    private  val categoryLiveData=MutableLiveData<Categories>()
    val categorydata:LiveData<Categories> get() = categoryLiveData
    private  val ProductsLiveData=MutableLiveData<ProductInfo>()
    val Productsdata:LiveData<ProductInfo> get() = ProductsLiveData
    suspend fun getcategory() {
        val result = apiService.getCategories()
        result?.body().let {
            categoryLiveData.postValue(result.body())
        }

    }
    suspend fun getProducts(){
        val result=apiService.getProducts()
        result?.body().let {
            ProductsLiveData.postValue(result.body())
        }
    }
}