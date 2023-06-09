package com.example.storeapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.storeapp.domain.Repository
import com.example.storeapp.domain.model.ShopItem
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper,
) : Repository {

    override suspend fun addShopItem(shopItem: ShopItem) {
        Log.d("AAAA", "addShopItem $shopItem ")
        coroutineScope {
            shopListDao.addShopItem(mapper.mapToEntityToDbModel(shopItem))
        }
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        Log.d("AAAA", "deleteShopItem $shopItem ")
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        Log.d("AAAA", "editShopItem $shopItem ")
        shopListDao.addShopItem(mapper.mapToEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(shopItemId)
        Log.d("AAAA", "getShopItem $dbModel ")
        return mapper.mapToModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = shopListDao.getShopList().map {
        mapper.mapListDbModelToListEntity(it)
    }
}