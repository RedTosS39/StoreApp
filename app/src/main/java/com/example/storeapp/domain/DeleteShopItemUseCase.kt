package com.example.storeapp.domain

import com.example.storeapp.domain.model.ShopItem

class DeleteShopItemUseCase (private val repository: Repository) {

    fun deleteShopItem(shopItem: ShopItem) {
        repository.deleteShopItem(shopItem)
    }
}