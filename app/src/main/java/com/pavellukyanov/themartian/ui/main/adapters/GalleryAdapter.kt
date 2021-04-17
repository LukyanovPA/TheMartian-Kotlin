package com.pavellukyanov.themartian.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.pavellukyanov.themartian.data.api.models.Photo
import com.pavellukyanov.themartian.databinding.RvGalleryItemBinding
import com.pavellukyanov.themartian.ui.main.adapters.GalleryAdapter.DataViewHolder
import com.pavellukyanov.themartian.ui.main.adapters.diff.GalleryDiffUtils

class GalleryAdapter(private var photos: List<Photo>) :
    RecyclerView.Adapter<DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding =
            RvGalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = photos.size

    fun getItem(position: Int): Photo = photos[position]

    fun addPhotos(newPhotos: List<Photo>) {
        val diffUtils = GalleryDiffUtils(photos, newPhotos)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        photos = newPhotos
        diffResult.dispatchUpdatesTo(this)
    }

    class DataViewHolder(private val binding: RvGalleryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            with(itemView) {
                Glide.with(context)
                    .asBitmap()
                    .load(photo.srcPhoto)
                    .transform(CenterCrop(), RoundedCorners(20))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.ivPhotoItem)

                binding.tvEarthDate.text = photo.dataEarth
            }
        }
    }
}