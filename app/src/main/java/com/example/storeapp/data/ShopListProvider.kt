package com.example.storeapp.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.example.storeapp.domain.model.ShopItem
import com.example.storeapp.presentation.app.StoreApplication
import javax.inject.Inject

class ShopListProvider : ContentProvider() {

    private val component by lazy {
        (context as StoreApplication).component
    }

    @Inject
    lateinit var shopListDao: ShopListDao

    @Inject
    lateinit var mapper: ShopListMapper

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        // /* - when string
        // /#" - when int
        addURI("com.example.storeapp", "shop_items", GET_SHOP_ITEMS_QUERY)
    }

    override fun onCreate(): Boolean {
        component.inject(this)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                Log.d("ShopListProvider", "query cursor:${shopListDao.getShopListCursor()} ")
                shopListDao.getShopListCursor()
            }
            else -> {
                null
            }
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                if (values == null) return null
                val id = values.getAsInteger(ID)
                val name = values.getAsString(NAME)
                val count = values.getAsInteger(COUNT)
                val enabled = values.getAsBoolean(ENABLE)

                val shopItem = ShopItem(
                    name = name,
                    count = count,
                    enabled = enabled,
                    id = id
                )

                shopListDao.addShopItemSync(mapper.mapToEntityToDbModel(shopItem))
            }

        }
        return null
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        when (uriMatcher.match(uri)) {
            GET_SHOP_ITEMS_QUERY -> {
                val id = selectionArgs?.get(0)?.toInt() ?: -1
                return shopListDao.deleteShopItemSync(id)
            }
        }

        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int {
        TODO("Not yet implemented")
    }

    companion object {
        const val GET_SHOP_ITEMS_QUERY = 0
        const val URI = "content://com.example.storeapp/shop_items"
        const val ID = "id"
        const val NAME = "name"
        const val COUNT = "count"
        const val ENABLE = "enabled"
    }
}