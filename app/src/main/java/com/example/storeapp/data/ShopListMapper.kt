package com.example.storeapp.data

import com.example.storeapp.domain.model.ShopItem
import javax.inject.Inject


class ShopListMapper @Inject constructor() {

    fun mapToEntityToDbModel(item: ShopItem) =
        ShopItemDbModel(
            id = item.id,
            name = item.name,
            count = item.count,
            enabled = item.enabled
        )

    fun mapToModelToEntity(item: ShopItemDbModel) =
        ShopItem(
            id = item.id,
            name = item.name,
            count = item.count,
            enabled = item.enabled
        )

    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>) = list.map {
        mapToModelToEntity(it)
    }
}