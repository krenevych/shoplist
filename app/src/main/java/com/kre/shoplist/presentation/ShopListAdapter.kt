package com.kre.shoplist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.kre.shoplist.R
import com.kre.shoplist.domain.Item

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ItemViewHolder>() {

    var list: List<Item> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_shop_enabled, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val enabled = if (list[position].enabled) {
            "enabled"
        } else {
            "disabled"
        }

        holder.tvName.text = "${list[position].name} $enabled"
        holder.tvCount.text = list[position].count.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
    }
}