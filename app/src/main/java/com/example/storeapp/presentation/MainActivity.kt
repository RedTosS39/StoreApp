package com.example.storeapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.R
import com.example.storeapp.constats.Constants
import com.example.storeapp.domain.model.ShopItem

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var adapter: ShopListAdapter
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        viewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
        viewModel.shopList.observe(this@MainActivity) {
            adapter.shopList = it
        }
    }

    private fun init() {
        adapter = ShopListAdapter()
        recyclerView = findViewById(R.id.recycler)
        recyclerView.adapter = adapter
        with(recyclerView) {

            setMaxRecycledViews(Constants.VIEW_TYPE_ENABLED, Constants.MAX_POOL_SIZE)
            setMaxRecycledViews(Constants.VIEW_TYPE_DISABLED, Constants.MAX_POOL_SIZE)
        }

    }

    private fun setMaxRecycledViews(viewType: Int, poolSize: Int) {
        recyclerView.recycledViewPool.setMaxRecycledViews(viewType, Constants.MAX_POOL_SIZE)
    }

}