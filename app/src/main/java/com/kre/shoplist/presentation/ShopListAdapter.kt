package com.kre.shoplist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kre.shoplist.R
import com.kre.shoplist.domain.Item


class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ItemViewHolder>() {

    companion object {
        const val TYPE_ACTIVE = 0
        const val TYPE_NON_ACTIVE = 1
    }

    var longClickListener : ( (Item) -> Unit )? = null
    var shortClickListener : ( (Item) -> Unit )? = null

    var list: List<Item> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

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
        val item = list[position]
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
        return if (list[position].enabled) {
            TYPE_ACTIVE
        } else {
            TYPE_NON_ACTIVE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
    }

    class SwipeCallback(val action: (Int) -> Unit) :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val adapterPosition = viewHolder.adapterPosition
            action(adapterPosition)
        }
    }
}