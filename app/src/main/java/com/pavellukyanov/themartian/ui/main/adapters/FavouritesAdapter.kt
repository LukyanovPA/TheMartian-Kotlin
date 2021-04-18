package com.pavellukyanov.themartian.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.domain.DomainPhoto
import com.pavellukyanov.themartian.databinding.FavouriteItemBinding
import com.pavellukyanov.themartian.ui.main.adapters.diff.FavouritesDiffUtils
import kotlinx.android.synthetic.main.favourite_item.view.*

class FavouritesAdapter(
    private var photos: List<DomainPhoto>,
    private val deleteFavouriteOnClickListener: DeleteFavouriteOnClickListener
) : RecyclerView.Adapter<FavouritesAdapter.FavouriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val binding =
            FavouriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.deleteFavourite.setOnClickListener {
            deleteFavouriteOnClickListener.deleteFavouriteOnClicked(
                getItem(holder.absoluteAdapterPosition).id
            )
        }
    }

    override fun getItemCount(): Int = photos.size

    private fun getItem(position: Int): DomainPhoto = photos[position]

    fun addPhotos(newPhotos: List<DomainPhoto>) {
        val diffUtils = FavouritesDiffUtils(photos, newPhotos)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        photos = newPhotos
        diffResult.dispatchUpdatesTo(this)
    }

    class FavouriteViewHolder(private val binding: FavouriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photoEntity: DomainPhoto) {
            with(itemView) {
                Glide.with(context)
                    .asBitmap()
                    .load(photoEntity.srcPhoto)
                    .transform(CenterCrop(), RoundedCorners(20))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.ivPhotoItem)

                binding.tvEarthDate.text = photoEntity.dataEarth
                binding.roverName.text = photoEntity.rover
            }
        }
    }
}