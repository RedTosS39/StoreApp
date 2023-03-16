package com.example.storeapp.domain.model

import com.example.storeapp.domain.Repository

class GetShopItemUseCase(private val repository: Repository) {

    fun getShopItem(shopItem: ShopItem) {
        repository.getShopItem(shopItem)
    }
}