package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.api.models.Photo
import com.pavellukyanov.themartian.data.domain.DomainPhoto
import com.pavellukyanov.themartian.databinding.FragmentFullPhotoBinding
import com.pavellukyanov.themartian.ui.main.adapters.AddFavouriteOnClickListener
import com.pavellukyanov.themartian.ui.main.viewmodel.FavouritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPhoto : Fragment(R.layout.fragment_full_photo) {
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private val args: FragmentPhotoArgs by navArgs()
    private lateinit var binding: FragmentFullPhotoBinding
    private var isFavourite = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFullPhotoBinding.bind(view)
        checkIsFavourite(args.photo.id)
        setupUI()
    }

    private fun checkIsFavourite(id: Long) {
        isFavourite = favouritesViewModel.checkIsFavourite(id)
    }

    private fun setupUI() {
        Glide.with(requireContext())
            .asBitmap()
            .load(args.photo.srcPhoto)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(binding.photo)
    }

    private val addFavouriteOnClickListener = object : AddFavouriteOnClickListener {
        override fun addToFavouriteOnClicked(photo: DomainPhoto) {
            TODO("Not yet implemented")
        }
    }
}