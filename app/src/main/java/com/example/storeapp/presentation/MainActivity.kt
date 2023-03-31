package com.example.storeapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.R
import com.example.storeapp.constats.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private var shopItemContainer: FragmentContainerView? = null
    private var fragment: ShopItemFragment? = null
    private lateinit var fab: FloatingActionButton
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopItemContainer = findViewById(R.id.shop_item_container)
        init()
        setupRecyclerView(savedInstanceState)
        setupFab(savedInstanceState)

        viewModel.shopList.observe(this@MainActivity) {
            shopListAdapter.submitList(it)
        }
    }


    private fun launchFragment(fragment: Fragment, savedInstanceState: Bundle?) {

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.shop_item_container, fragment)
                .commit()
        }
    }

    private fun isOnePaneMode(): Boolean {
        return shopItemContainer == null
    }

    private fun setupRecyclerView(savedInstanceState: Bundle?) {

        with(recyclerView) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            setMaxRecycledViews(Constants.VIEW_TYPE_ENABLED)
            setMaxRecycledViews(Constants.VIEW_TYPE_DISABLED)
        }

        setupLongClickListener()
        setupClickListener(savedInstanceState)
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

    private fun setupFab(savedInstanceState: Bundle?) {
        fab.setOnClickListener {
            if(isOnePaneMode()) {
                fragment = ShopItemFragment.newInstanceAddItem()
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem(), savedInstanceState)
            }
        }
    }

    private fun setupClickListener(savedInstanceState: Bundle?) {
        shopListAdapter.onShopItemShortClick = {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditItem(this@MainActivity, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id), savedInstanceState)
            }
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

    private fun init() {
        recyclerView = findViewById(R.id.recycler)
        fab = findViewById(R.id.floating_action_button)
        viewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
    }
}