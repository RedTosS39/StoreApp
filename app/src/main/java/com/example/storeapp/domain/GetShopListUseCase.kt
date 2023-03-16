package com.example.storeapp.domain

import com.example.storeapp.domain.model.ShopItem

class GetShopListUseCase(private val repository: Repository) {

    fun getShopList() : List<ShopItem> {
        return repository.getShopList()
    }
}