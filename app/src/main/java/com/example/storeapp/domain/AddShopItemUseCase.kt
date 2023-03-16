package com.example.storeapp.domain

import com.example.storeapp.domain.model.ShopItem

class AddShopItemUseCase(private val repository: Repository) {

    fun addShopItem(shopItem: ShopItem) {
        repository.addShopItem(shopItem)
    }
}