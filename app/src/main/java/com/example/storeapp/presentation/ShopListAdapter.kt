package com.example.storeapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.R
import com.example.storeapp.constats.Constants.VIEW_TYPE_DISABLED
import com.example.storeapp.constats.Constants.VIEW_TYPE_ENABLED
import com.example.storeapp.domain.model.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemShortClick : ((ShopItem) -> Unit)?= null

    class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
    }

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
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = shopList[position]
        return when (shopItem.enabled) {
            true -> VIEW_TYPE_ENABLED
            else -> VIEW_TYPE_DISABLED
        }
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.tvCount.text = shopList[position].id.toString()
        holder.tvName.text = shopList[position].name
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        holder.itemView.setOnClickListener {
            onShopItemShortClick?.invoke(shopItem)

        }

    }

    override fun getItemCount(): Int {
        return shopList.size
    }

//    interface OnShopItemLongClickListener {
//        fun onShopItemLongClick(shopItem: ShopItem)
//    }
//
//    interface OnShopItemShortClickListener {
//        fun onShopItemShortClick(shopItem: ShopItem)
//    }
}