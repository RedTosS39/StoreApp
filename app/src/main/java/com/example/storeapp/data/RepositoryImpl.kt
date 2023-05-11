package com.example.storeapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.storeapp.domain.Repository
import com.example.storeapp.domain.model.ShopItem
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper

) : Repository {
    override suspend fun addShopItem(shopItem: ShopItem) {
       coroutineScope {
           shopListDao.addShopItem(mapper.mapToEntityToDbModel(shopItem))
       }
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapToEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val item = shopListDao.getShopItem(shopItemId)
        return mapper.mapToModelToEntity(item)
    }

    override fun getShopList(): LiveData<List<ShopItem>> =
        Transformations.map(shopListDao.getShopList()) {
            mapper.mapListDbModelToListEntity(it)
        }
}