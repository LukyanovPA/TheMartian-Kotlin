package com.pavellukyanov.themartian.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pavellukyanov.themartian.data.model.RoverInfo
import com.pavellukyanov.themartian.databinding.MainPageViewHolderBinding

class MainAdapter(
    private val roverInfo: MutableList<RoverInfo>,
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            MainPageViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onItemClicked(getItem(position))
        }
    }

    private fun getItem(position: Int): RoverInfo = roverInfo[position]

    override fun getItemCount(): Int = roverInfo.size

    fun addRoversInfo(rovers: MutableList<RoverInfo>) {
        this.roverInfo.apply {
            clear()
            addAll(rovers)
        }
    }

    class MainViewHolder(private val binding: MainPageViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(roverInfo: RoverInfo) {
            with(binding) {
                Glide.with(itemView.context)
                    .asBitmap()
                    .load(roverInfo.picture)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .into(roverPicture)

                roverNameMain.append(roverInfo.name)
                launchDate.append(roverInfo.launchData)
                latestPhotoDate.append(roverInfo.maxDate.toString())
                totalPhotos.append(roverInfo.totalPhotos.toString())
            }
        }

    }
}

interface ItemClickListener {
    fun onItemClicked(roverInfo: RoverInfo)
}