package com.example.loginapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.loginapp.Api.ApiService
import com.example.loginapp.Api.RetrofitHelper
import com.example.loginapp.databinding.ActivityHomeBinding
import com.example.loginapp.repository.ProductRepository
import com.example.loginapp.viewmodel.ProductViewModel
import com.example.loginapp.viewmodel.ProductViewModelFactory

class Home : AppCompatActivity() {
    lateinit var productViewModel: ProductViewModel
    lateinit var binding :ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        val repository = ProductRepository(apiService)
        productViewModel = ViewModelProvider(
            this,
            ProductViewModelFactory(repository)
        ).get(ProductViewModel::class.java)
        productViewModel.category.observe(this) {

            it?.let {
                Log.d("masud", it.toString())

            }
        }
        productViewModel.products.observe(this){
            it.products.let {
                Log.d("masud 1",it.toString())
            }
        }
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavicationBar.background=null
        replaceFragment(Category())
        binding.bottomNavicationBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.category -> {
                    replaceFragment(Category())
                }
                R.id.product -> {
                    replaceFragment(Product())
                }
            }
            true // Return true to indicate that the selection event has been handled
        }

    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_Layout, fragment)
        fragmentTransaction.commit()
    }

}
