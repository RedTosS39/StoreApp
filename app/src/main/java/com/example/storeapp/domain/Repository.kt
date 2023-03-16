package com.example.storeapp.domain

import com.example.storeapp.domain.model.ShopItem

interface Repository {

    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun getShopList() : List<ShopItem>

    fun getShopItem(shopItem: ShopItem) : ShopItem

    fun editShopItem(shopItem: ShopItem)
}