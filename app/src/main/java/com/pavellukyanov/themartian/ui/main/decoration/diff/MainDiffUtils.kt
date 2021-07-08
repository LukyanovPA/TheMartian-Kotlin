package com.pavellukyanov.themartian.ui.main.decoration.diff

import androidx.recyclerview.widget.DiffUtil
import com.pavellukyanov.themartian.domain.rover_info.RoverInfo

class MainDiffUtils(
    private val oldList: List<RoverInfo>,
    private val newList: List<RoverInfo>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].roverName == newList[newItemPosition].roverName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].roverName == newList[newItemPosition].roverName -> true
            oldList[oldItemPosition].landingDate == newList[newItemPosition].landingDate -> true
            oldList[oldItemPosition].launchData == newList[newItemPosition].launchData -> true
            oldList[oldItemPosition].maxDate == newList[newItemPosition].maxDate -> true
            oldList[oldItemPosition].maxSol == newList[newItemPosition].maxSol -> true
            oldList[oldItemPosition].status == newList[newItemPosition].status -> true
            oldList[oldItemPosition].totalPhotos == newList[newItemPosition].totalPhotos -> true
            else -> false
        }
    }
}