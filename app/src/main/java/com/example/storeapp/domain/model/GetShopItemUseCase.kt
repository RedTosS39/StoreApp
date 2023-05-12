package com.example.storeapp.domain.model

import com.example.storeapp.domain.Repository
import javax.inject.Inject

class GetShopItemUseCase @Inject constructor(private val repository: Repository) {

    suspend fun getShopItem(shopItemId: Int) : ShopItem{
        return repository.getShopItem(shopItemId)
    }
}