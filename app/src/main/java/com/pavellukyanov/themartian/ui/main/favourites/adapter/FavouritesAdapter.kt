package com.pavellukyanov.themartian.ui.main.favourites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.pavellukyanov.themartian.domain.photo.Photo
import com.pavellukyanov.themartian.databinding.FavouriteItemBinding
import com.pavellukyanov.themartian.ui.main.decoration.diff.FavouritesDiffUtils
import com.pavellukyanov.themartian.ui.base.DeleteFavouriteOnClickListener
import com.pavellukyanov.themartian.ui.base.ItemClickListener

class FavouritesAdapter(
    private var photos: List<Photo>,
    private val deleteFavouriteOnClickListener: DeleteFavouriteOnClickListener,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<FavouritesAdapter.FavouriteViewHolder>() {
    private lateinit var binding: FavouriteItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        binding =
            FavouriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(getItem(position))

        binding.deleteFavourite.setOnClickListener {
            deleteFavouriteOnClickListener.deleteFavouriteOnClicked(
                getItem(holder.absoluteAdapterPosition)
            )
        }
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(getItem(holder.absoluteAdapterPosition))
        }
    }

    override fun getItemCount(): Int = photos.size

    private fun getItem(position: Int): Photo = photos[position]

    fun addPhotos(newPhotos: List<Photo>) {
        val diffUtils = FavouritesDiffUtils(photos, newPhotos)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        photos = newPhotos
        diffResult.dispatchUpdatesTo(this)
    }

    class FavouriteViewHolder(private val binding: FavouriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photoEntity: Photo) {
            with(binding) {
                Glide.with(itemView.context)
                    .asBitmap()
                    .load(photoEntity.srcPhoto)
                    .transform(CenterCrop(), RoundedCorners(20))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(ivPhotoItem)

                tvEarthDate.text = photoEntity.dataEarth
                roverName.text = photoEntity.rover
            }
        }
    }
}