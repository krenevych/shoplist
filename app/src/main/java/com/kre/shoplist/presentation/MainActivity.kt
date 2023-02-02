package com.kre.shoplist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.kre.shoplist.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "XXXXX"
    }

    private lateinit var viewModel: MainViewModel
    private var itemModificationContainer: FragmentContainerView? = null
    private val adapter = ShopListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemModificationContainer = findViewById(R.id.item_modification_container)

        initVM()
        initRecyclerView()
        buttonAddInit()

    }

    private fun initRecyclerView() {
        rv_shop_list.adapter = adapter

        adapter.longClickListener = viewModel::toggleEnabled
        adapter.shortClickListener = {
            if (isOnePaneMode()) {
                startActivity(IntentFactory.createEditIntent(this, it.id))
            } else {
                launchFragment(ItemModificationFragment.newFragmentEditItem(it.id))
            }
        }

        val swipeCallback = SwipeCallback { pos ->
            val item = adapter.currentList[pos]
            viewModel.removeItem(item)
            Toast.makeText(this, "Remove Item $item", Toast.LENGTH_SHORT).show()
        }

        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(rv_shop_list)
    }

    private fun initVM() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun buttonAddInit() {

        if (isOnePaneMode()) {
            button_add_shop_item.setOnClickListener {
                startActivity(IntentFactory.createAddIntent(this))
            }
        } else {
            launchFragment(ItemModificationFragment.newFragmentAddItem())
        }
    }

    private fun isOnePaneMode(): Boolean = itemModificationContainer == null
    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.item_modification_container, fragment)
            .commit()
    }

}