package com.example.storeapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.R
import com.example.storeapp.constats.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var fab: FloatingActionButton
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setupRecyclerView()
        setupFab()

        viewModel.shopList.observe(this@MainActivity) {
            shopListAdapter.submitList(it)
        }
    }

    private fun init() {
        recyclerView = findViewById(R.id.recycler)
        fab = findViewById(R.id.floating_action_button)
        viewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
    }

    private fun setupRecyclerView() {

        with(recyclerView) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            setMaxRecycledViews(Constants.VIEW_TYPE_ENABLED)
            setMaxRecycledViews(Constants.VIEW_TYPE_DISABLED)
        }

        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(recyclerView)
    }

    private fun setupSwipeListener(recyclerView: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val currentItem = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(currentItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupFab() {
        fab.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }
    private fun setupClickListener() {
        shopListAdapter.onShopItemShortClick = {
            val intent = ShopItemActivity.newIntentEditItem(this@MainActivity, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    private fun setMaxRecycledViews(viewType: Int) {
        recyclerView.recycledViewPool.setMaxRecycledViews(viewType, Constants.MAX_POOL_SIZE)
    }
}