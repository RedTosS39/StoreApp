package com.example.storeapp.domain.model

import com.example.storeapp.domain.Repository

class GetShopItemUseCase(private val repository: Repository) {

    fun getShopItem(shopItemId: Int) : ShopItem{
        return repository.getShopItem(shopItemId)
    }
}