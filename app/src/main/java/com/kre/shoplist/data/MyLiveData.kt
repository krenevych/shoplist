package com.kre.shoplist.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kre.shoplist.domain.Item

class MyLiveData : MutableLiveData<List<Item>>() {

    companion object {
        private const val TAG = "XXXXLIVE"
    }

    override fun onActive() {
        Log.e(TAG, "onActive: ")
        super.onActive()
    }

    override fun onInactive() {
        Log.e(TAG, "onInactive: ")
        super.onInactive()
    }
}