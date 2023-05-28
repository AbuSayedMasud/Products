package com.example.loginapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginapp.models.Categories
import com.example.loginapp.models.ProductInfo
import com.example.loginapp.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(val repository: ProductRepository) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategories()
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProducts()
        }
    }
    val category:LiveData<Categories> get()=repository.categoryLiveData
    val products:LiveData<ProductInfo> get()=repository.productsLiveData
}