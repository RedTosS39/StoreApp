package com.example.storeapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.R

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

            adapter.shopList  = it
        }
    }

    private fun init() {
        recyclerView = findViewById(R.id.recycler)
        recyclerView.adapter = adapter
    }
}