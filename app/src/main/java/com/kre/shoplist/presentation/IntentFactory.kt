package com.kre.shoplist.presentation

import android.content.Context
import android.content.Intent
import com.kre.shoplist.presentation.Constants.ITEM_ID
import com.kre.shoplist.presentation.Constants.MODE
import com.kre.shoplist.presentation.Constants.MODE_ADD
import com.kre.shoplist.presentation.Constants.MODE_EDIT

object IntentFactory {
    fun createAddIntent(context: Context) : Intent {
        return Intent(context, ItemModificationActivity::class.java).apply {
            putExtra(MODE, MODE_ADD)
        }
    }

    fun createEditIntent(context: Context, itemId: Int) : Intent {
        return Intent(context, ItemModificationActivity::class.java).apply {
            putExtra(MODE, MODE_EDIT)
            putExtra(ITEM_ID, itemId)
        }
    }
}