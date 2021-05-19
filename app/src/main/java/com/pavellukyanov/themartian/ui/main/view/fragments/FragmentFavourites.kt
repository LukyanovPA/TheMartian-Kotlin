package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.domain.Photo
import com.pavellukyanov.themartian.databinding.FragmentFavouritesBinding
import com.pavellukyanov.themartian.ui.main.adapters.DeleteFavouriteOnClickListener
import com.pavellukyanov.themartian.ui.main.adapters.FavouritesAdapter
import com.pavellukyanov.themartian.ui.main.adapters.ItemClickListener
import com.pavellukyanov.themartian.ui.main.viewmodel.ExchangeViewModel
import com.pavellukyanov.themartian.ui.main.viewmodel.FavouritesViewModel
import com.pavellukyanov.themartian.utils.Constants.Companion.GRID_COLUMNS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentFavourites : Fragment(R.layout.fragment_favourites) {
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private val exchangeViewModel: ExchangeViewModel by activityViewModels()
    private val favouritesAdapter by lazy {
        FavouritesAdapter(
            listOf(),
            deleteFavouriteOnClickListener,
            itemClickListener
        )
    }
    private lateinit var binding: FragmentFavouritesBinding
    private var favouritesList = mutableListOf<Photo>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouritesBinding.bind(view)
        setupIU()
        subscribeFavouritesData()
    }

    private fun subscribeFavouritesData() {
        favouritesViewModel.getAllFavourites().observe(viewLifecycleOwner, { listPhoto ->
            favouritesList = listPhoto.toMutableList()
            retrieveList(favouritesList)
            val cameras: HashSet<String> = hashSetOf()
            val rovers: HashSet<String> = hashSetOf()
            listPhoto.forEach {
                cameras.add(it.camera)
                rovers.add(it.rover)
            }
            exchangeViewModel.selectFavouritesCameras(cameras)
            exchangeViewModel.selectRovers(rovers)
        })

        exchangeViewModel.returnChooseFavCam().observe(viewLifecycleOwner, { choosedCam ->
            val filteredList = mutableListOf<Photo>()
            choosedCam.forEach { choos ->
                favouritesList.forEach { photo ->
                    if (photo.camera == choos) {
                        filteredList.add(photo)
                    }
                }
            }
            if (choosedCam.isEmpty()) {
                retrieveList(favouritesList)
            } else {
                retrieveList(filteredList)
            }
        })

        exchangeViewModel.returnChoosedRovers().observe(viewLifecycleOwner, { choosedRovers ->
            val filteredList = mutableListOf<Photo>()
            choosedRovers.forEach { choos ->
                favouritesList.forEach { photo ->
                    if (photo.rover == choos) {
                        filteredList.add(photo)
                    }
                }
            }
            if (choosedRovers.isEmpty()) {
                retrieveList(favouritesList)
            } else {
                retrieveList(filteredList)
            }
        })
    }

    private fun setupIU() {
        binding.rvFavourites.apply {
            adapter = favouritesAdapter
            layoutManager = GridLayoutManager(context, GRID_COLUMNS)
        }
    }

    private val deleteFavouriteOnClickListener = object : DeleteFavouriteOnClickListener {
        override fun deleteFavouriteOnClicked(id: Long) {
            favouritesViewModel.deletePhoto(id)
            Snackbar.make(
                binding.scrollLayoutFav,
                getString(R.string.snack_delete),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private val itemClickListener = object : ItemClickListener {
        override fun onItemClicked(photo: Photo) {
            //доделать
        }
    }

    private fun retrieveList(list: List<Photo>) {
        favouritesAdapter.apply {
            addPhotos(list)
        }
    }
}