package com.example.storeapp.domain.model

data class ShopItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = A) {

    companion object {
        const val A = -1
    }
}