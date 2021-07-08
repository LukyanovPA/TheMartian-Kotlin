package com.pavellukyanov.themartian.ui.main.decoration.diff

import androidx.recyclerview.widget.DiffUtil
import com.pavellukyanov.themartian.domain.photo.Photo

class GalleryDiffUtils(
    private val oldList: List<Photo>,
    private val newList: List<Photo>
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id == newList[newItemPosition].id -> true
            oldList[oldItemPosition].srcPhoto == newList[newItemPosition].srcPhoto -> true
            oldList[oldItemPosition].camera == newList[newItemPosition].camera -> true
            oldList[oldItemPosition].dataEarth == newList[newItemPosition].dataEarth -> true
            oldList[oldItemPosition].rover == newList[newItemPosition].rover -> true
            oldList[oldItemPosition].sol == newList[newItemPosition].sol -> true
            else -> false
        }
    }
}