package com.example.storeapp.domain

import com.example.storeapp.domain.model.ShopItem
import kotlinx.coroutines.coroutineScope

class DeleteShopItemUseCase (private val repository: Repository) {

    suspend fun deleteShopItem(shopItem: ShopItem) {
        repository.deleteShopItem(shopItem)
    }
}