package com.pavellukyanov.themartian.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pavellukyanov.themartian.data.models.dbmodel.RoverInfoEntity
import com.pavellukyanov.themartian.data.models.networkmodel.RoverInfo
import com.pavellukyanov.themartian.databinding.MainPageViewHolderBinding

class MainAdapter(
    private var roverInfo: List<RoverInfoEntity>,
    private val clickListener: NewItemClickListener
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            MainPageViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onClicked(getItem(position))
        }
    }

    private fun getItem(position: Int): RoverInfoEntity = roverInfo[position]

    override fun getItemCount(): Int = roverInfo.size


//    fun addRoversInfo(rovers: List<RoverInfoEntity>) {
//        this.roverInfo.apply {
//            clear()
//            addAll(rovers)
//        }
//    }

    class MainViewHolder(private val binding: MainPageViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(roverInfo: RoverInfoEntity) {
            Log.d("ttt", "Reci - ${roverInfo.name}")
            with(binding) {
                Glide.with(itemView.context)
                    .asBitmap()
                    .load(roverInfo.roverPictureInMainPage)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .into(roverPicture)

                roverNameMain.text = roverInfo.name
                launchDate.text = roverInfo.launchData
                latestPhotoDate.text = roverInfo.maxDate.toString()
                totalPhotos.text = roverInfo.totalPhotos.toString()
            }
        }

    }
}

interface ItemClickListener {
    fun onItemClicked(roverInfo: RoverInfo)
}

interface NewItemClickListener {
    fun onClicked(roverInfo: RoverInfoEntity)
}