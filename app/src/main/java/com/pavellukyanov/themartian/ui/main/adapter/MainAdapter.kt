package com.pavellukyanov.themartian.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.models.network.RoverInfo
import kotlinx.android.synthetic.main.main_page_view_holder.view.*

class MainAdapter(
    private val roverInfo: MutableList<RoverInfo>,
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        Log.d("ttt", "Adapter - ${roverInfo.size}")
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.main_page_view_holder, parent, false)
        return MainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        Log.d("ttt", "Get item - ${getItem(1)}")
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onItemClicked(getItem(position))
        }
    }

    private fun getItem(position: Int): RoverInfo = roverInfo[position]

    override fun getItemCount(): Int = roverInfo.size

    fun addRoversInfo(rovers: List<RoverInfo>) {
        this.roverInfo.apply {
            clear()
            addAll(rovers)
        }
    }

    class MainViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(roverInfo: RoverInfo) {
            Log.d("ttt", "Holder - ${roverInfo.name}")
            with(itemView) {
                Glide.with(itemView.context)
                    .asBitmap()
                    .load(initDefaultRoverInfo(roverInfo.name))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .into(roverPicture)

                roverNameMain.append(roverInfo.name)
                launchDate.append(roverInfo.launchData)
                latestPhotoDate.append(roverInfo.maxDate)
                totalPhotos.append(roverInfo.totalPhotos.toString())
            }
        }

        private fun initDefaultRoverInfo(roverName: String): Int {
            return when (roverName) {
                CURIOSITY -> R.drawable.curiosity
                OPPORTUNITY -> R.drawable.opportunity
                SPIRIT -> R.drawable.spirit
                else -> 0
            }
        }

        companion object {
            private const val CURIOSITY = "Curiosity"
            private const val OPPORTUNITY = "Opportunity"
            private const val SPIRIT = "Spirit"
        }

    }
}

interface ItemClickListener {
    fun onItemClicked(roverInfo: RoverInfo)
}