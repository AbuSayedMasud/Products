package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapp.Api.ApiService
import com.example.loginapp.Api.RetrofitHelper
import com.example.loginapp.databinding.FragmentProductBinding
import com.example.loginapp.models.Product
import com.example.loginapp.repository.ProductRepository
import com.example.loginapp.viewmodel.ProductViewModel
import com.example.loginapp.viewmodel.ProductViewModelFactory

class Product : Fragment(), ProductAdapter.OnItemClickListener {

    private lateinit var binding: FragmentProductBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productViewModel: ProductViewModel
    private var productList: MutableList<Product> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        val repository = ProductRepository(apiService)
        productViewModel = ViewModelProvider(
            this,
            ProductViewModelFactory(repository)
        ).get(ProductViewModel::class.java)

        initializeRecyclerView()

        productViewModel.products.observe(viewLifecycleOwner, Observer { products ->
            products?.let {
                productList.addAll(products.products)
                Log.d("tag", products.products.size.toString())
                productAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun initializeRecyclerView() {
        productAdapter = ProductAdapter(productList, this)
        binding.recyclerProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productAdapter
        }
    }

    override fun onItemClick(product: Product) {

        var intent=Intent( requireContext(),ProductDescription::class.java).apply {
            putExtra("BRAND",product.brand)
            putExtra("CATEGORY",product.category)
            putExtra("DESCRIPTION",product.description)
            putExtra("PRICE",product.price)
            putExtra("TITLE",product.title)
            putExtra("Image",product.thumbnail)
            putExtra("Stock",product.stock.toString())
            putExtra("Rating",product.rating.toString())

        }
        startActivity(intent)
    }
}
