package com.pavellukyanov.themartian.ui.main.mainpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.databinding.ItemRoverInfoBinding
import com.pavellukyanov.themartian.domain.RoverInfo
import com.pavellukyanov.themartian.ui.main.decoration.diff.MainDiffUtils
import com.pavellukyanov.themartian.ui.main.mainpage.RoverInfoClickListener
import com.pavellukyanov.themartian.utils.Constants.Companion.CURIOSITY
import com.pavellukyanov.themartian.utils.Constants.Companion.OPPORTUNITY
import com.pavellukyanov.themartian.utils.Constants.Companion.PERSEVERANCE
import com.pavellukyanov.themartian.utils.Constants.Companion.SPIRIT
import com.pavellukyanov.themartian.utils.loadCircle

class MainAdapter(
    private var roverInfo: List<RoverInfo>,
    private val clickListener: RoverInfoClickListener
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ItemRoverInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onRoverInfoItemClicked(
                getItem(holder.absoluteAdapterPosition)
            )
        }
    }

    private fun getItem(position: Int): RoverInfo = roverInfo[position]

    override fun getItemCount(): Int = roverInfo.size

    fun addRoversInfo(rovers: List<RoverInfo>) {
        val diffUtils = MainDiffUtils(roverInfo, rovers)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        roverInfo = rovers
        diffResult.dispatchUpdatesTo(this)
    }

    class MainViewHolder(private val binding: ItemRoverInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(roverInfo: RoverInfo) {
            with(binding) {
                Glide
                    .with(itemView.context)
                    .asBitmap()
                    .load(
                        when (roverInfo.roverName) {
                            CURIOSITY -> R.drawable.curiosity
                            OPPORTUNITY -> R.drawable.opportunity
                            SPIRIT -> R.drawable.spirit
                            PERSEVERANCE -> R.drawable.perseverance
                            else -> null
                        }
                    )
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(roverPicture)

                when (roverInfo.roverName) {
                    CURIOSITY -> roverIsActive.text = itemView.context.getString(R.string.is_active)
                    OPPORTUNITY -> roverIsActive.text =
                        itemView.context.getString(R.string.is_no_active)
                    SPIRIT -> roverIsActive.text = itemView.context.getString(R.string.is_no_active)
                    PERSEVERANCE -> roverIsActive.text =
                        itemView.context.getString(R.string.is_active)
                }

                roverName.text = roverInfo.roverName
                launchDate.text = roverInfo.launchData
                latestPhotoDate.text = roverInfo.maxDate
                totalPhotos.text = roverInfo.totalPhotos
            }
        }
    }
}