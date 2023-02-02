package com.kre.shoplist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kre.shoplist.R
import com.kre.shoplist.domain.Item

class ItemModificationActivity : AppCompatActivity() {

    private val TAG: String = "MMMMMMMMM"

    private lateinit var mode: String
    private var itemId: Int = Item.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_modification)


        mode = intent.getStringExtra(Constants.EXTRA_MODE) ?: Constants.MODE_ADD
        itemId = intent.getIntExtra(Constants.EXTRA_ITEM_ID_NAME, Item.UNDEFINED_ID)

        launchFragment()
    }

    private fun launchFragment() {
        when (mode) {
            Constants.MODE_EDIT -> supportFragmentManager
                .beginTransaction()
                .add(R.id.item_modification_container, ItemModificationFragment(mode, itemId))
                .commit()

            Constants.MODE_ADD -> supportFragmentManager
                .beginTransaction()
                .add(R.id.item_modification_container, ItemModificationFragment(mode))
                .commit()
        }
    }

}
