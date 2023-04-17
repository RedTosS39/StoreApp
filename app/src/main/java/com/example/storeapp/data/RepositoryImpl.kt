package com.example.storeapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.storeapp.domain.Repository
import com.example.storeapp.domain.model.ShopItem

class RepositoryImpl(application: Application) : Repository {

    private val shopListDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapToEntityToDbModel(shopItem))
    }

    override fun deleteShopItem(shopItem: ShopItem) {

        shopListDao.deleteShopItem(shopItem.id)
    }

    override fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapToEntityToDbModel(shopItem))
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        val item = shopListDao.getShopItem(shopItemId)
        return mapper.mapToModelToEntity(item)
    }

    override fun getShopList(): LiveData<List<ShopItem>> =
        Transformations.map(shopListDao.getShopList()) {
            mapper.mapListDbModelToListEntity(it)
        }
}