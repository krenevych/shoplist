package com.kre.shoplist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.kre.shoplist.domain.Item

class ListDiffCallback(
    private val oldList: List<Item>,
    private val newList: List<Item>,
) : DiffUtil.Callback()
{
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        TODO("Not yet implemented")
    }
}