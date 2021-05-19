package com.pavellukyanov.themartian.ui.main.roverdetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.pavellukyanov.themartian.data.domain.Photo
import com.pavellukyanov.themartian.databinding.RvGalleryItemBinding
import com.pavellukyanov.themartian.ui.main.roverdetails.adapter.GalleryAdapter.DataViewHolder
import com.pavellukyanov.themartian.ui.main.decoration.diff.GalleryDiffUtils
import com.pavellukyanov.themartian.ui.main.roverdetails.AddFavouriteOnClickListener
import com.pavellukyanov.themartian.ui.main.roverdetails.ItemClickListener
import kotlinx.android.synthetic.main.rv_gallery_item.view.*

class GalleryAdapter(
    private var photos: List<Photo>,
    private val favouriteClickListener: AddFavouriteOnClickListener,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding =
            RvGalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.favourite.setOnClickListener {
            favouriteClickListener.addToFavouriteOnClicked(
                getItem(holder.absoluteAdapterPosition)
            )
        }
        holder.itemView.setOnClickListener {
            val photoList = arrayListOf<String>()
            photos.forEach {
                photoList.add(it.srcPhoto)
            }
            itemClickListener.onItemClicked(getItem(holder.absoluteAdapterPosition))
        }
    }

    override fun getItemCount(): Int = photos.size

    private fun getItem(position: Int): Photo = photos[position]

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
                binding.cameraName.text = photo.camera
            }
        }
    }
}