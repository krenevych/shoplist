package com.kre.shoplist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
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

    private fun initRecyclerView() {
        rv_shop_list.adapter = ShopListAdapter(viewModel.shopList)
    }

    private fun initVM() {

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this)
        {
        }

    }


    private fun buttonInit() = button_add_shop_item.setOnClickListener {
        viewModel.addItem(Item("New", 999))
    }


}