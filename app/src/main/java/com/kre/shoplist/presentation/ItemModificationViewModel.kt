package com.kre.shoplist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kre.shoplist.data.ShopListRepositoryImpl
import com.kre.shoplist.domain.Item
import com.kre.shoplist.domain.Manager

class ItemModificationViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl
    private val manager = Manager(repository)

    fun getItem(itemId: Int) {
        _item.value = manager.getItem(itemId)
    }

    fun addItem(name: String?, counter: String?) {
        val nameStr = parseName(name)
        val count = parseCount(counter)
        if (checkItemData(nameStr, count)) {
            val item = Item(nameStr, count)
            manager.addItem(item)
            _closeEvent.value = true
        }
    }

    fun editItem(name: String?, counter: String?) {
        val nameStr = parseName(name)
        val count = parseCount(counter)
        if (checkItemData(nameStr, count)) {
            item.value?.let {
                val newItem = it.copy(name = nameStr, count = count)
                manager.updateItem(newItem)
                _closeEvent.value = true
            }

        }
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(counter: String?): Int {
        return try {
            counter?.toInt() ?: 0
        } catch (nfe: NumberFormatException) {
            0
        }
    }

    fun checkItemData(name: String, counter: Int): Boolean {
        var res = true
        if (name.isEmpty()) {
            _errorNameLiveData.value = true
            res = false
        }
        if (counter <= 0) {
            _errorCountLiveData.value = true
            res = false
        }
        return res
    }


    private val _errorNameLiveData = MutableLiveData<Boolean>()
    val errorNameLiveData: LiveData<Boolean>
        get() = _errorNameLiveData

    fun resetNameLiveData() {
        _errorNameLiveData.value = false
    }

    private val _errorCountLiveData = MutableLiveData<Boolean>()
    val errorCountLiveData: LiveData<Boolean>
        get() = _errorCountLiveData

    fun resetCountLiveData() {
        _errorCountLiveData.value = false
    }

    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item>
        get() = _item

    private val _closeEvent = MutableLiveData(false)
    val closeEvent: LiveData<Boolean>
        get() = _closeEvent

    fun resetCloseEvent() {
        _closeEvent.value = false
    }

}