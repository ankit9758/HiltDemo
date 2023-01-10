package com.example.hiltdemo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.hiltdemo.R
import com.example.hiltdemo.databinding.ActivityMainBinding
import com.example.hiltdemo.models.Products
import com.example.hiltdemo.states.ProductState
import com.example.hiltdemo.ui.adapter.ProductListAdapter
import com.example.hiltdemo.utils.hide
import com.example.hiltdemo.utils.show
import com.example.hiltdemo.utils.toast
import com.example.hiltdemo.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var productListAdapter: ProductListAdapter
    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewModel()
        setupViews()
        setupObservers()

    }

    private fun setUpViewModel() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    //initialize the adapter and attach with recyclerview
    private fun setupViews() {
        productListAdapter = ProductListAdapter(this)
        binding.rvProduct.apply {
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL))
            adapter = productListAdapter
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getProductList()
            binding.swipeRefresh.isRefreshing=false
        }

    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            with(binding) {
                swipeRefresh.isRefreshing=false
                viewModel.productState.collect {
                    when (it) {
                        is ProductState.Error -> {
                            progressBar.hide()
                            val message = it.throwable.message ?: ""
                            tvNoData.text = message
                            tvNoData.show()
                            toast(message)
                        }
                        ProductState.ShowLoading -> {
                            progressBar.show()
                        }
                        is ProductState.Success -> {
                            progressBar.hide()
                            showNoDataFound(it.data)
                            productListAdapter.setProductList(it.data)
                        }
                        ProductState.Idle -> {

                        }
                    }
                }
            }

        }
    }

    private fun showNoDataFound(itemList: List<Products>) {
        with(binding) {
            if (itemList.isEmpty()) {
                tvNoData.text = "No Data Found."
                tvNoData.show()
                rvProduct.hide()
            } else {
                tvNoData.hide()
                rvProduct.show()
            }
        }

    }

}