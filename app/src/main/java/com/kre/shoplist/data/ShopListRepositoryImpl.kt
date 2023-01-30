package com.kre.shoplist.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.kre.shoplist.domain.Item
import com.kre.shoplist.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList = sortedSetOf<Item>({ left, right -> left.id.compareTo(right.id) })

    private val liveData = MyLiveData()
    private var currentId = 0

    init {
        Log.d("XXXXXLIVE", "Init")
        for (id in 1..15){
            addItem(Item("Name $id", id, Random.nextBoolean()))
        }
    }

    init {
        Log.e("XXXXXLIVE", "Init2")
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

    override fun getItem(id: Int): Item? {
        val find = shopList.find {
            it.id == id
        }

        find.let {
            return it
        }

    }

    override fun removeItem(id: Int) {
        val item = getItem(id)
        item?.let {
            removeItem(it)
        }
    }

    override fun removeItem(item: Item) {
        shopList.remove(item)
        updateLiveData()
    }

    override fun getItems(): LiveData<List<Item>> {
        return liveData
    }

    private fun updateLiveData() {
        liveData.value = shopList.toList()
    }

}