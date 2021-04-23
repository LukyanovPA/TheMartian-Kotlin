package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.domain.DomainPhoto
import com.pavellukyanov.themartian.databinding.FragmentRoverDetailsBinding
import com.pavellukyanov.themartian.ui.main.adapters.AddFavouriteOnClickListener
import com.pavellukyanov.themartian.ui.main.adapters.GalleryAdapter
import com.pavellukyanov.themartian.ui.main.adapters.ItemClickListener
import com.pavellukyanov.themartian.ui.main.viewmodel.ExchangeViewModel
import com.pavellukyanov.themartian.ui.main.viewmodel.RoverDetailsViewModel
import com.pavellukyanov.themartian.utils.Constants.Companion.GRID_COLUMNS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentRoverDetails : Fragment(R.layout.fragment_rover_details) {
    private val exchangeViewModel: ExchangeViewModel by activityViewModels()
    private val detailViewModel: RoverDetailsViewModel by viewModels()
    private lateinit var binding: FragmentRoverDetailsBinding
    private val galleryAdapter by lazy {
        GalleryAdapter(
            arrayListOf(),
            addToFavouriteOnClickListener,
            itemClickListener
        )
    }
    private var photosList = mutableListOf<DomainPhoto>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRoverDetailsBinding.bind(view)
        setupUI()
        subscribeExchangeData()
    }

    private fun setupUI() {
        binding.rvDetails.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(context, GRID_COLUMNS)
        }
    }

    private fun subscribeExchangeData() {
        exchangeViewModel.returnActualDate().observe(viewLifecycleOwner, {
            subscribeMarsData(it.first, it.second)
        })
    }

    private fun subscribeMarsData(roverName: String, date: String) {
        detailViewModel.getPhotosForEarthData(roverName, date)
            .observe(viewLifecycleOwner, { listPhotos ->
                photosList = listPhotos.toMutableList()
                retrieveList(photosList)
                val cameras: HashSet<String> = hashSetOf()
                listPhotos.forEach {
                    cameras.add(it.camera)
                }
                exchangeViewModel.selectNetworkCameras(cameras)
            })

        exchangeViewModel.returnChooseCam().observe(viewLifecycleOwner, { choosedCam ->
            val filteredList = mutableListOf<DomainPhoto>()
            choosedCam.forEach { choos ->
                photosList.forEach { photo ->
                    if (photo.camera == choos) {
                        filteredList.add(photo)
                    }
                }
            }
            if (choosedCam.isEmpty()) {
                retrieveList(photosList)
            } else {
                retrieveList(filteredList)
            }
        })
    }

    private val addToFavouriteOnClickListener = object : AddFavouriteOnClickListener {
        override fun addToFavouriteOnClicked(photo: DomainPhoto) {
            detailViewModel.addPhotoToFavourite(photo)
            Snackbar.make(
                binding.scrollLayout,
                getString(R.string.snack_add_favourite),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private val itemClickListener = object : ItemClickListener {
        override fun onItemClicked(domainPhoto: DomainPhoto) {
            //доделать
        }
    }

    private fun retrieveList(photos: List<DomainPhoto>) {
        galleryAdapter.apply {
            addPhotos(photos)
        }
    }
}