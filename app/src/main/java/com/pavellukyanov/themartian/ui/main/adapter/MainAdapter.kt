package com.pavellukyanov.themartian.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.model.RoverInfo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.main_page_view_holder.view.*

class MainAdapter(
    private val roverInfo: MutableList<RoverInfo>,
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_page_view_holder, parent, false)
        return MainViewHolder(inflater)
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

    class MainViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(roverInfo: RoverInfo) {
            with(containerView) {
                Glide.with(context)
                    .asBitmap()
                    .load(
                        when (roverInfo.name) {
                            CURIOSITY -> R.drawable.curiosity
                            OPPORTUNITY -> R.drawable.opportunity
                            SPIRIT -> R.drawable.spirit
                            else -> null
                        }
                    )
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .into(roverPicture)

                roverNameMain.append(roverInfo.name)
                launchDate.append(roverInfo.launchData)
                latestPhotoDate.append(roverInfo.maxDate)
                totalPhotos.append(roverInfo.totalPhotos.toString())
            }
        }

    }

    companion object {
        private const val CURIOSITY = "Curiosity"
        private const val OPPORTUNITY = "Opportunity"
        private const val SPIRIT = "Spirit"
    }
}

interface ItemClickListener {
    fun onItemClicked(roverInfo: RoverInfo)
}