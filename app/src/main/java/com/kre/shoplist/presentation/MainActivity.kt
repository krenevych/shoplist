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

class MainActivity : AppCompatActivity(), ItemModificationFragment.FinishListener {

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
                launchFragment(ItemModificationFragment.newFragmentEditItem(it.id), Constants.EDIT_ITEM_FRAGMENT_NAME)
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

        button_add_shop_item.setOnClickListener {
            if (isOnePaneMode()) {
                startActivity(IntentFactory.createAddIntent(this))
            } else {
                launchFragment(ItemModificationFragment.newFragmentAddItem(), Constants.ADD_ITEM_FRAGMENT_NAME)
            }
        }
    }

    private fun isOnePaneMode(): Boolean = itemModificationContainer == null
    private fun launchFragment(fragment: Fragment, name: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.item_modification_container, fragment)
            .addToBackStack(name)
            .commit()
    }

    override fun onFinish(mode: String) {
        Toast.makeText(this, "$mode: Saved", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

}