package com.pavellukyanov.themartian.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.api.models.RoverInfo
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.main_page_view_holder.view.*
import com.pavellukyanov.themartian.utils.Constants.Companion.CURIOSITY
import com.pavellukyanov.themartian.utils.Constants.Companion.OPPORTUNITY
import com.pavellukyanov.themartian.utils.Constants.Companion.SPIRIT

class MainAdapter(
    private val roverInfo: MutableList<RoverInfoEntity>,
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

    private fun getItem(position: Int): RoverInfoEntity = roverInfo[position]

    override fun getItemCount(): Int = roverInfo.size

    fun addRoversInfo(rovers: MutableList<RoverInfoEntity>) {
        this.roverInfo.apply {
            clear()
            addAll(rovers)
        }
    }

    class MainViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(roverInfo: RoverInfoEntity) {
            with(containerView) {
                Glide.with(context)
                    .asBitmap()
                    .load(
                        when (roverInfo.roverName) {
                            CURIOSITY -> R.drawable.curiosity
                            OPPORTUNITY -> R.drawable.opportunity
                            SPIRIT -> R.drawable.spirit
                            else -> null
                        }
                    )
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .into(roverPicture)

                roverNameMain.text = roverInfo.roverName
                launchDate.text = roverInfo.launchData
                latestPhotoDate.text = roverInfo.maxDate
                totalPhotos.text = roverInfo.totalPhotos
            }
        }

    }
}

interface ItemClickListener {
    fun onItemClicked(roverInfo: RoverInfoEntity)
}