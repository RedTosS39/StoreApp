package com.example.storeapp.domain

import androidx.lifecycle.LiveData
import com.example.storeapp.domain.model.ShopItem

class GetShopListUseCase(private val repository: Repository) {

    fun getShopList() : LiveData<List<ShopItem>> {
        return repository.getShopList()
    }
}