package com.example.storeapp.domain

import com.example.storeapp.domain.model.ShopItem

class EditShopItemUseCase(private val repository: Repository) {

    suspend fun editShopItem(shopItem: ShopItem) {
        repository.editShopItem(shopItem)
    }
}