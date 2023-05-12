package com.example.storeapp.domain

import com.example.storeapp.domain.model.ShopItem
import javax.inject.Inject

class EditShopItemUseCase @Inject constructor(private val repository: Repository) {

    suspend fun editShopItem(shopItem: ShopItem) {
        repository.editShopItem(shopItem)
    }
}