package com.example.storeapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class ShopItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID,
)  {

    companion object {
        const val UNDEFINED_ID = 0
    }
}