package com.example.storeapp.presentation.view

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.MyBroadcastReceivers
import com.example.storeapp.R
import com.example.storeapp.constats.Constants
import com.example.storeapp.databinding.ActivityMainBinding
import com.example.storeapp.presentation.adapters.ShopListAdapter
import com.example.storeapp.presentation.app.StoreApplication
import com.example.storeapp.presentation.viewmodel.MainViewModel
import com.example.storeapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var binding: ActivityMainBinding
    private val receiver = MyBroadcastReceivers()

    private val component by lazy {
        (application as StoreApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this@MainActivity, viewModelFactory)[MainViewModel::class.java]
    }

    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(MyBroadcastReceivers.ACTION_ADD_ITEM)
        }

        registerReceiver(receiver, intentFilter)

        setContentView(binding.root)
        setupRecyclerView()
        setupFab()

        viewModel.shopList.observe(this@MainActivity) {
            shopListAdapter.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }


    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun isOnePaneMode(): Boolean {
        return binding.shopItemContainer == null
    }

    private fun setupRecyclerView() {

        with(binding.recycler) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            setMaxRecycledViews(Constants.VIEW_TYPE_ENABLED)
            setMaxRecycledViews(Constants.VIEW_TYPE_DISABLED)
        }

        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(binding.recycler)
    }

    private fun setupSwipeListener(recyclerView: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
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
        binding.floatingActionButton.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemShortClick = {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditItem(this@MainActivity, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)

            val bundle = Bundle().apply {
                putString(MyBroadcastReceivers.KEY_BROADCAST_NAME, it.name)
                putBoolean(MyBroadcastReceivers.KEY_BROADCAST_ENABLED, it.enabled)
            }

            Intent(MyBroadcastReceivers.ACTION_ADD_ITEM).apply {
                putExtra(MyBroadcastReceivers.KEY_BROADCAST_BUNDLE, bundle)
                sendBroadcast(this)
            }
        }
    }

    private fun setMaxRecycledViews(viewType: Int) {
        binding.recycler.recycledViewPool.setMaxRecycledViews(viewType, Constants.MAX_POOL_SIZE)
    }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
}