package com.kre.shoplist.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kre.shoplist.domain.Item
import com.kre.shoplist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList = mutableListOf<Item>()
    private val shopListLD = MutableLiveData<List<Item>>()
    private var currentId = 0

    init {
        Log.d("XXWWWX", "Init")
        for (i in 0..10){
            addItem(Item("Item$i", i))
        }
    }

    override fun addItem(item: Item) {
        if (item.id == Item.UNDEFINED_ID) {
            item.id = currentId++
        }
        shopList.add(item)
        updateLiveData()
    }

    override fun updateItem(item: Item) {
        removeItem(item.id)
        addItem(item)
    }

    override fun getItem(id: Int): Item {
        return shopList.find {
            it.id == id
        } ?: throw RuntimeException("Element with id $id not found!")
    }

    override fun removeItem(id: Int) {
        val item = getItem(id)
        shopList.remove(item)
        updateLiveData()
    }

    override fun getItems(): LiveData<List<Item>> {
        return shopListLD
    }

    private fun updateLiveData(){
        shopListLD.value = shopList.toList()
    }

}