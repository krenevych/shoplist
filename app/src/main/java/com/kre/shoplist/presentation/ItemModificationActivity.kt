package com.kre.shoplist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kre.shoplist.R
import com.kre.shoplist.domain.Item

class ItemModificationActivity : AppCompatActivity(), ItemModificationFragment.FinishListener {

    private val TAG: String = "MMMMMMMMM"

    private lateinit var mode: String
    private var itemId: Int = Item.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_modification)


        mode = intent.getStringExtra(Constants.MODE) ?: Constants.MODE_ADD
        itemId = intent.getIntExtra(Constants.ITEM_ID, Item.UNDEFINED_ID)

        launchFragment()
    }

    private fun launchFragment() {
        when (mode) {
            Constants.MODE_EDIT ->  launchFragment(ItemModificationFragment.newFragmentEditItem(itemId))
            Constants.MODE_ADD -> launchFragment(ItemModificationFragment.newFragmentAddItem())
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.item_modification_container, fragment)
            .commit()
    }

    override fun onFinish(mode: String) {
        Toast.makeText(this, "Saved form ItemModificationActivity", Toast.LENGTH_SHORT).show()
    }

}
