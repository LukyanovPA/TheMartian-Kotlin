package com.pavellukyanov.themartian.ui.main.fullphoto

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.domain.photo.Photo
import com.pavellukyanov.themartian.databinding.FragmentFullPhotoBinding
import com.pavellukyanov.themartian.ui.base.BaseFragment
import com.pavellukyanov.themartian.ui.base.AddFavouriteOnClickListener
import com.pavellukyanov.themartian.ui.main.favourites.FavouritesViewModel
import com.pavellukyanov.themartian.utils.loadCircle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPhoto : Fragment(R.layout.fragment_full_photo) {
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private lateinit var binding: FragmentFullPhotoBinding
    private var isFavourite = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFullPhotoBinding.bind(view)
//        checkIsFavourite(args.photo.id)
//        setupUI()
    }

    private fun checkIsFavourite(id: Long) {
//        favouritesViewModel.checkIsFavourite(id).observe(viewLifecycleOwner, { onStateReceive(it) })
    }

//    override fun handleSuccessState(data: Boolean) {
//        super.handleSuccessState(data)
//        isFavourite = data
//    }

    private fun setupUI() {
//        binding.photo.loadCircle(
//            args.photo.srcPhoto
//        )
    }

    private val addFavouriteOnClickListener = object : AddFavouriteOnClickListener {
        override fun addToFavouriteOnClicked(photo: Photo) {
            TODO("Not yet implemented")
        }
    }
}