package com.pavellukyanov.themartian.ui.main.fullphoto

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.domain.Photo
import com.pavellukyanov.themartian.data.domain.RoverInfo
import com.pavellukyanov.themartian.data.repository.ResourceState
import com.pavellukyanov.themartian.databinding.FragmentFullPhotoBinding
import com.pavellukyanov.themartian.ui.main.roverdetails.AddFavouriteOnClickListener
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
        favouritesViewModel.checkIsFavourite(id).observe(viewLifecycleOwner, { onStateReceive(it) })
    }

    private fun onStateReceive(resourceState: ResourceState<Boolean>) {
        when (resourceState) {
            is ResourceState.Success -> handleSuccessState(resourceState.data)
            is ResourceState.Loading -> handleLoadingState(true)
            is ResourceState.Error -> handleErrorState(resourceState.error)
        }
    }

    private fun handleSuccessState(state: Boolean) {
        handleLoadingState(false)
        isFavourite = state
    }

    private fun handleLoadingState(state: Boolean) {

    }

    private fun handleErrorState(error: Throwable?) {
        handleLoadingState(false)
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.error_toast, error?.localizedMessage),
            Toast.LENGTH_LONG
        ).show()
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
        override fun addToFavouriteOnClicked(photo: Photo) {
            TODO("Not yet implemented")
        }
    }
}