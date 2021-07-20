package com.pavellukyanov.themartian.ui.main.gallery

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.databinding.FragmentGalleryBinding
import com.pavellukyanov.themartian.domain.photo.Photo
import com.pavellukyanov.themartian.ui.base.BaseFragment
import com.pavellukyanov.themartian.ui.base.DeleteFavouriteOnClickListener
import com.pavellukyanov.themartian.ui.main.gallery.adapter.GalleryAdapter
import com.pavellukyanov.themartian.ui.base.AddFavouriteOnClickListener
import com.pavellukyanov.themartian.ui.base.ItemClickListener
import com.pavellukyanov.themartian.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentGallery : BaseFragment<List<Photo>, GalleryViewModel>(R.layout.fragment_gallery) {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val args: FragmentGalleryArgs by navArgs()
    private val viewModel: GalleryViewModel by viewModels()
    private val roverName by lazy { args.roverInfo.roverName }
    private val photoDate by lazy { args.roverInfo.maxDate }
    private val minDate by lazy { args.roverInfo.landingDate }
    private val adapter by lazy {
        GalleryAdapter(
            mutableListOf(),
            addToFavouriteOnClickListener,
            deleteFavouriteOnClickListener,
            itemClickListener
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGalleryBinding.bind(view)
        setupUI()
        getPhotos(roverName, photoDate)
    }

    private fun getPhotos(roverName: String, date: String) {
        viewModel.doChangePhotoDate(roverName, date)
    }

    override fun handleSuccessState(data: List<Photo>) {
        super.handleSuccessState(data)
        adapter.addPhotos(data)
        viewModel.setupNetworkCameras(data)
    }

    override fun handleLoadingState(state: Boolean) {
        super.handleLoadingState(state)
        with(binding) {
            progressBar.isVisible = state
            progressText.isVisible = state
        }
    }

    private fun setupUI() {
        with(binding) {
            recyclerGallery.apply {
                adapter = adapter
                layoutManager = GridLayoutManager(context, Constants.GRID_COLUMNS)
            }

            buttonBack.setOnClickListener {
                activity?.onBackPressed()
            }

            fabSetting.setOnClickListener {
                changeFab(false, requireContext())
            }

            fabDate.setOnClickListener {
                getPhotos(
                    roverName,
                    chooseDate(photoDate, minDate, requireContext())
                )
            }

            fabCamera.setOnClickListener {
                chooseCamera(
                    requireContext(),
                    viewModel.availableNetworkCameras(),
                    adapter
                )
            }
        }
    }

    private val addToFavouriteOnClickListener = object : AddFavouriteOnClickListener {
        override fun addToFavouriteOnClicked(photo: Photo) {
//            detailViewModel.addPhotoToFavourite(photo)
//            Snackbar.make(
//                binding.scrollLayout,
//                getString(R.string.snack_add_favourite),
//                Snackbar.LENGTH_SHORT
//            ).show()
        }
    }

    private val deleteFavouriteOnClickListener = object : DeleteFavouriteOnClickListener {
        override fun deleteFavouriteOnClicked(photo: Photo) {
//            favouritesViewModel.deletePhoto(photo)
//            Snackbar.make(
//                binding.scrollLayoutFav,
//                getString(R.string.snack_delete),
//                Snackbar.LENGTH_SHORT
//            ).show()
        }
    }

    private val itemClickListener = object : ItemClickListener {
        override fun onItemClicked(photo: Photo) {
            //доделать
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModel.onDestroy()
    }
}