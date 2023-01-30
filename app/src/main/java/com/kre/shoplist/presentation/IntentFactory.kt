package com.kre.shoplist.presentation

import android.content.Context
import android.content.Intent
import com.kre.shoplist.presentation.Constants.EXTRA_ITEM_ID_NAME
import com.kre.shoplist.presentation.Constants.EXTRA_MODE
import com.kre.shoplist.presentation.Constants.MODE_ADD
import com.kre.shoplist.presentation.Constants.MODE_EDIT

object IntentFactory {
    fun createAddIntent(context: Context) : Intent {
        return Intent(context, ItemModificationActivity::class.java).apply {
            putExtra(EXTRA_MODE, MODE_ADD)
        }
    }

    fun createEditIntent(context: Context, itemId: Int) : Intent {
        return Intent(context, ItemModificationActivity::class.java).apply {
            putExtra(EXTRA_MODE, MODE_EDIT)
            putExtra(EXTRA_ITEM_ID_NAME, itemId)
        }
    }
}