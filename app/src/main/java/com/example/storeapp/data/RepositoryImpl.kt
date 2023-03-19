package com.example.storeapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.storeapp.domain.Repository
import com.example.storeapp.domain.model.ShopItem
import kotlin.random.Random

object RepositoryImpl : Repository {

    private val shopListLiveData = MutableLiveData<List<ShopItem>>()
    private var shopList = sortedSetOf(Comparator<ShopItem> { o1, o2 -> o1.id.compareTo(o2.id) })

    private var autoIncrementIt = 0

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementIt++
        }

        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItem(shopItem.id)
        shopList.remove(oldShopItem)
        autoIncrementIt++
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element with id: $shopItemId not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {

        val testlist = mutableListOf<ShopItem>()
        for (i in 0 .. 100) {
            val item = ShopItem("Element", autoIncrementIt, Random.nextBoolean())
            if (item.id == ShopItem.UNDEFINED_ID) {
                item.id = autoIncrementIt++
            }
            testlist.add(item)
        }

        shopListLiveData.postValue(testlist.toList())

        return shopListLiveData
    }

    private fun updateList() {

        shopListLiveData.postValue(shopList.toList())
    }
}