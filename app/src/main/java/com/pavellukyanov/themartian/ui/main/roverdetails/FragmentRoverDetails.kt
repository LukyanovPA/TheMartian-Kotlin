package com.pavellukyanov.themartian.ui.main.roverdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.databinding.FragmentRoverDetailsBinding
import com.pavellukyanov.themartian.domain.photo.Photo
import com.pavellukyanov.themartian.ui.base.BaseFragment
import com.pavellukyanov.themartian.ui.main.roverdetails.adapter.GalleryAdapter
import com.pavellukyanov.themartian.ui.main.viewmodel.ExchangeViewModel
import com.pavellukyanov.themartian.utils.Constants.Companion.GRID_COLUMNS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentRoverDetails : BaseFragment<List<Photo>>(R.layout.fragment_rover_details) {
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
    private var photosList = mutableListOf<Photo>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRoverDetailsBinding.bind(view)
        initRecycler()
        subscribeExchangeData()
    }

    private fun initRecycler() {
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
            .observe(viewLifecycleOwner, (this::onStateReceive))
    }

    private fun changeRoverCamera() {
        exchangeViewModel.returnChooseCam().observe(viewLifecycleOwner, { choosedCam ->
            val filteredList = mutableListOf<Photo>()
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

    private fun setRoverCameras(listPhoto: List<Photo>): HashSet<String> {
        val cameras: HashSet<String> = hashSetOf()
        listPhoto.forEach {
            cameras.add(it.camera)
        }
        return cameras
    }

    private val addToFavouriteOnClickListener = object : AddFavouriteOnClickListener {
        override fun addToFavouriteOnClicked(photo: Photo) {
            detailViewModel.addPhotoToFavourite(photo)
            Snackbar.make(
                binding.scrollLayout,
                getString(R.string.snack_add_favourite),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private val itemClickListener = object : ItemClickListener {
        override fun onItemClicked(photo: Photo) {
            //доделать
        }
    }

    override fun handleSuccessState(data: List<Photo>) {
        super.handleSuccessState(data)
        exchangeViewModel.selectNetworkCameras(setRoverCameras(data))
        changeRoverCamera()
        photosList = data.toMutableList()
        retrieveList(photosList)
    }

    private fun retrieveList(listPhoto: List<Photo>) {
        galleryAdapter.apply {
            addPhotos(listPhoto)
        }
    }

    override fun onDetach() {
        super.onDetach()
        detailViewModel.onDestroy()
    }
}