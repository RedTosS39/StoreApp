package com.example.storeapp.data

import com.example.storeapp.domain.model.ShopItem


class ShopListMapper {

    fun mapToEntityToDbModel(item: ShopItem) =
        ShopItemDbModel(
            item.id,
            item.name,
            item.count,
            item.enabled
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