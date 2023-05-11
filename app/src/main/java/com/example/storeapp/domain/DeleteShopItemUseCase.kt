package com.example.storeapp.domain

import com.example.storeapp.domain.model.ShopItem
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DeleteShopItemUseCase @Inject constructor(private val repository: Repository) {
    suspend fun deleteShopItem(shopItem: ShopItem) {
        repository.deleteShopItem(shopItem)
    }
}