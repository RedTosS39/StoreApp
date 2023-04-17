package com.example.storeapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.storeapp.data.RepositoryImpl
import com.example.storeapp.domain.DeleteShopItemUseCase
import com.example.storeapp.domain.EditShopItemUseCase
import com.example.storeapp.domain.GetShopListUseCase
import com.example.storeapp.domain.Repository
import com.example.storeapp.domain.model.ShopItem

class MainViewModel() : ViewModel() {

    private val repository: Repository = RepositoryImpl
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }
}