package com.example.loginapp

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.loginapp.Api.ApiService
import com.example.loginapp.Api.RetrofitHelper
import com.example.loginapp.databinding.ActivityHomeBinding
import com.example.loginapp.repository.ProductRepository
import com.example.loginapp.viewmodel.ProductViewModel
import com.example.loginapp.viewmodel.ProductViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class Home : AppCompatActivity() {
    lateinit var productViewModel: ProductViewModel
    lateinit var binding: ActivityHomeBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationView

        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener { item ->
            drawerLayout.closeDrawer(GravityCompat.START)
            when (item.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Home is Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_message -> {
                    Toast.makeText(this, "Message is Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.synch -> {
                    Toast.makeText(this, "Synch is Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.trash -> {
                    Toast.makeText(this, "Trash is Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.settings -> {
                    Toast.makeText(this, "Settings is Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_login -> {
                    Toast.makeText(this, "Login is Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_share -> {
                    Toast.makeText(this, "Share is clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_rate -> {
                    Toast.makeText(this, "Rate us is Clicked", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    return@setNavigationItemSelectedListener true
                }
            }
            true
        }

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
        productViewModel.products.observe(this) {
            it.products.let {
                Log.d("masud 1", it.toString())
            }
        }

        binding.bottomNavicationBar.background = null
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
