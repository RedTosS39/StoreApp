package com.example.storeapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.storeapp.R
import com.example.storeapp.constats.Constants.VIEW_TYPE_DISABLED
import com.example.storeapp.constats.Constants.VIEW_TYPE_ENABLED
import com.example.storeapp.databinding.ItemShopDisabledBinding
import com.example.storeapp.databinding.ItemShopEnabledBinding
import com.example.storeapp.domain.model.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemShortClick: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> {
                R.layout.item_shop_enabled
            }
            VIEW_TYPE_DISABLED -> {
                R.layout.item_shop_disabled
            }
            else -> {
                throw RuntimeException("Unknown viewType: $viewType")
            }
        }
//        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return when (shopItem.enabled) {
            true -> VIEW_TYPE_ENABLED
            else -> VIEW_TYPE_DISABLED
        }
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val binding = holder.binding
        val shopItem = getItem(position)

        when (binding) {
            is ItemShopDisabledBinding -> {
                binding.shopItem = shopItem
            }
            is ItemShopEnabledBinding -> {
                binding.shopItem = shopItem
            }
        }
        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        binding.root.setOnClickListener {
            onShopItemShortClick?.invoke(shopItem)
        }
    }
}