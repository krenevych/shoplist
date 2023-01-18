package com.kre.shoplist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.kre.shoplist.R
import com.kre.shoplist.domain.Item

class ShopListAdapter(private val data : LiveData<List<Item>>) : RecyclerView.Adapter<ShopListAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shop_enabled, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val list: List<Item>? = data.value

        holder.tvName.text = ""
        holder.tvCount.text = ""

        list?.let {
            holder.tvName.text = list[position].name
            holder.tvCount.text = list[position].count.toString()
        }


    }

    override fun getItemCount(): Int {
        val list: List<Item>? = data.value
        return list?.size ?: 0
    }

    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
    }
}