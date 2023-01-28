package com.kre.shoplist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kre.shoplist.R
import com.kre.shoplist.domain.Item


class ShopListAdapter : ListAdapter<Item, ItemViewHolder>(ItemDiffCallback()) {

    companion object {
        const val TYPE_ACTIVE = 0
        const val TYPE_NON_ACTIVE = 1
    }

    var longClickListener : ( (Item) -> Unit )? = null
    var shortClickListener : ( (Item) -> Unit )? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutId = when (viewType) {
            TYPE_ACTIVE -> R.layout.item_shop_enabled
            TYPE_NON_ACTIVE -> R.layout.item_shop_disabled
            else -> throw java.lang.RuntimeException("Unknown view type type")
        }

        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)


        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.tvName.text = item.name
        holder.tvCount.text = item.count.toString()

        holder.view.setOnLongClickListener {
            longClickListener?.invoke(item)
            true
        }

        holder.view.setOnClickListener {
            shortClickListener?.invoke(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enabled) {
            TYPE_ACTIVE
        } else {
            TYPE_NON_ACTIVE
        }
    }


}