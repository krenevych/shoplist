package com.kre.shoplist.domain

import androidx.lifecycle.LiveData

class Manager(private val repository: ShopListRepository) {

    fun addItem(item: Item) {
        repository.addItem(item)
    }

    fun updateItem(item: Item) {
        repository.updateItem(item)
    }

    fun getItem(id: Int): Item? {
        return repository.getItem(id)
    }

    fun removeItem(id: Int) {
        repository.removeItem(id)
    }

    fun removeItem(item: Item) {
        repository.removeItem(item)
    }

    fun getItems(): LiveData<List<Item>> {
        return repository.getItems()
    }
}