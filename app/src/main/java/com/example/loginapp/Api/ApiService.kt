package com.example.loginapp.Api

import com.example.loginapp.models.Categories
import com.example.loginapp.models.ProductInfo
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/products/categories")
    suspend fun getCategories():Response<Categories>
    @GET("/products")
    suspend fun getProducts():Response<ProductInfo>

}