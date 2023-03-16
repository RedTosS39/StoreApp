package com.example.storeapp.domain

import androidx.lifecycle.LiveData
import com.example.storeapp.domain.model.ShopItem

interface Repository {

    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId: Int) : ShopItem

    fun getShopList() : LiveData<List<ShopItem>>
}