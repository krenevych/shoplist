package com.kre.shoplist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun addItem(item: Item)
    fun updateItem(item: Item)
    fun getItem(id: Int) : Item?
    fun removeItem(id: Int)
    fun getItems() : LiveData<List<Item>>
}