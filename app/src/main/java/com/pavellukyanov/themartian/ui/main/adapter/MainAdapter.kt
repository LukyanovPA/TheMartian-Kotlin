package com.pavellukyanov.themartian.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.model.Mars
import com.pavellukyanov.themartian.data.model.Photo
import com.pavellukyanov.themartian.ui.main.adapter.MainAdapter.DataViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.rv_gallery_item.view.*
import rx.plugins.RxJavaHooks.clear
import java.util.Collections.addAll

class MainAdapter(private val photos: ArrayList<Photo>) : RecyclerView.Adapter<DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_gallery_item, parent, false)
        return DataViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = photos.size

    fun getItem(position: Int): Photo = photos[position]

    fun addPhotos(photos: List<Photo>) {

        this.photos.apply {
            clear()
            addAll(photos)
        }
    }

    class DataViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(photo: Photo) {
            with(containerView) {
                Glide.with(context)
                    .asBitmap()
                    .load(photo.srcPhoto)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .into(ivPhotoItem)

                tvEarthDate.text = photo.dataEarth
            }
        }
    }
}