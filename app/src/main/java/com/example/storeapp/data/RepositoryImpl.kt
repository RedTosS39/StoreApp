package com.example.storeapp.data

import com.example.storeapp.domain.Repository
import com.example.storeapp.domain.model.ShopItem

class RepositoryImpl : Repository {
    override fun addShopItem(shopItem: ShopItem) {
        TODO("Not yet implemented")
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        TODO("Not yet implemented")
    }

    override fun getShopList() : List<ShopItem> {
        TODO("Not yet implemented")
    }

    override fun getShopItem(shopItem: ShopItem) : ShopItem {
        return shopItem
    }

    override fun editShopItem(shopItem: ShopItem) {
        TODO("Not yet implemented")
    }
}