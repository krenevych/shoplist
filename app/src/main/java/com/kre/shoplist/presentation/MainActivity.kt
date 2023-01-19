package com.kre.shoplist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.ItemTouchHelper
import com.kre.shoplist.R
import com.kre.shoplist.domain.Item
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "XXXXX"
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initVM()
        initRecyclerView()
        buttonInit()

    }

    private val adapter = ShopListAdapter()

    private fun initRecyclerView() {
        rv_shop_list.adapter = adapter

        adapter.longClickListener = viewModel::toggleEnabled

        adapter.shortClickListener = {
            Toast.makeText(this, "Item: $it", Toast.LENGTH_LONG).show()
        }

        val swipeCallback = ShopListAdapter.SwipeCallback {pos ->
            val item = adapter.list[pos]
            viewModel.removeItem(item)
            Toast.makeText(this, "Remove Item $item", Toast.LENGTH_LONG).show()

        }

        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(rv_shop_list)
    }

    private fun initVM() {

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this)
        {
            adapter.list = it
        }
    }


    private fun buttonInit() = button_add_shop_item.setOnClickListener {
        viewModel.addItem(Item("New", 999))
    }


}