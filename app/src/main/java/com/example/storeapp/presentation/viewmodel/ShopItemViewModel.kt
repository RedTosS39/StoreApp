package com.example.storeapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.domain.AddShopItemUseCase
import com.example.storeapp.domain.EditShopItemUseCase
import com.example.storeapp.domain.model.GetShopItemUseCase
import com.example.storeapp.domain.model.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopItemViewModel @Inject constructor(
    private val editShopItemUseCase: EditShopItemUseCase,
    private val getShopItemUseCase: GetShopItemUseCase,
    private val addShopItemUseCase: AddShopItemUseCase,
) : ViewModel() {

    private val _inputNameError = MutableLiveData<Boolean>()
    private val _inputCountError = MutableLiveData<Boolean>()

    val inputNameError: LiveData<Boolean>
        get() = _inputNameError
    val inputCountError: LiveData<Boolean>
        get() = _inputCountError
    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem
    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun getShopItem(shopItemId: Int) {
        viewModelScope.launch {
            val item = getShopItemUseCase.getShopItem(shopItemId)
            _shopItem.postValue(item)
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseString(inputName)
        val count = parseInt(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            viewModelScope.launch {
                _shopItem.value?.let {
                    val newShopItem = it.copy(name = name, count = count)
                    editShopItemUseCase.editShopItem(newShopItem)
                    isFinished()
                }
            }
        }
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseString(inputName)
        val count = parseInt(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            viewModelScope.launch {
                val shopItem = ShopItem(name, count, true)
                addShopItemUseCase.addShopItem(shopItem)
                isFinished()
            }
        }
    }

    private fun parseString(string: String?): String {
        return string?.trim() ?: ""
    }

    private fun parseInt(string: String?): Int {
        return try {
            string?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _inputNameError.postValue(true)
            result = false
        }
        if (count <= 0) {
            _inputCountError.postValue(true)
            result = false
        }
        return result
    }

    fun resetInputNameError() {
        _inputNameError.postValue(false)
    }

    fun resetInputCountError() {
        _inputCountError.postValue(false)
    }

    private fun isFinished() {
        _shouldCloseScreen.postValue(Unit)
    }
}