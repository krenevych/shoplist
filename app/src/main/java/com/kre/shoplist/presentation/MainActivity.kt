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

    }

    private fun initVM() {

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this)
        {
            showList(it)
        }

    }

    private fun showList(list: List<Item>) {
        ll_container.removeAllViews()
        for (item in list) {
            val layoutId =
                if (item.enabled) {
                    R.layout.item_shop_enabled
                } else {
                    R.layout.item_shop_disabled
                }

            val itemView = LayoutInflater.from(this).inflate(layoutId, ll_container, false)
            val tvName: TextView =  itemView.findViewById(R.id.tv_name)
            val tvCount: TextView =  itemView.findViewById(R.id.tv_count)

            tvName.text = item.name
            tvCount.text = item.count.toString()

            ll_container.addView(itemView)

            itemView.setOnLongClickListener {
                viewModel.toggleEnabled(item)
                true
            }

        }
    }

    private fun buttonInit() = button_add_shop_item.setOnClickListener {
        viewModel.addItem(Item("New", 999))
    }


}