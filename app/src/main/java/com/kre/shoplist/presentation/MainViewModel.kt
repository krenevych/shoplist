package com.kre.shoplist.presentation

import androidx.lifecycle.ViewModel
import com.kre.shoplist.data.ShopListRepositoryImpl
import com.kre.shoplist.domain.Item
import com.kre.shoplist.domain.Manager

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val manager = Manager(repository)
    val shopList = manager.getItems()

    fun addItem(item: Item) {
        manager.addItem(item)
    }

    fun getItem(id: Int): Item {
        return manager.getItem(id)
    }

    fun removeItem(id: Int) {
        manager.removeItem(id)
    }

    fun toggleEnabled(item: Item) {
        manager.updateItem(item.copy(enabled = !item.enabled))
    }


}