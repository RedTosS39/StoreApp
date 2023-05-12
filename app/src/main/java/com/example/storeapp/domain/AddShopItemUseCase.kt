package com.example.storeapp.domain

import com.example.storeapp.domain.model.ShopItem
import javax.inject.Inject

class AddShopItemUseCase @Inject constructor(private val repository: Repository) {

    suspend fun addShopItem(shopItem: ShopItem) {
        repository.addShopItem(shopItem)
    }
}