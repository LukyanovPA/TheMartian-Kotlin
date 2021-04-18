package com.pavellukyanov.themartian.ui.main.adapters.diff

import androidx.recyclerview.widget.DiffUtil
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.domain.DomainPhoto

class FavouritesDiffUtils(
    private val oldList: List<DomainPhoto>,
    private val newList: List<DomainPhoto>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id == newList[newItemPosition].id -> true
            oldList[oldItemPosition].dataEarth == newList[newItemPosition].dataEarth -> true
            oldList[oldItemPosition].rover == newList[newItemPosition].rover -> true
            oldList[oldItemPosition].sol == newList[newItemPosition].sol -> true
            oldList[oldItemPosition].srcPhoto == newList[newItemPosition].srcPhoto -> true
            else -> false
        }
    }
}