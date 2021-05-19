package com.pavellukyanov.themartian.ui.main.favourites

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.domain.Photo
import com.pavellukyanov.themartian.data.repository.ResourceState
import com.pavellukyanov.themartian.databinding.FragmentFavouritesBinding
import com.pavellukyanov.themartian.ui.main.favourites.adapter.FavouritesAdapter
import com.pavellukyanov.themartian.ui.main.roverdetails.ItemClickListener
import com.pavellukyanov.themartian.ui.main.viewmodel.ExchangeViewModel
import com.pavellukyanov.themartian.ui.main.viewmodel.FavouritesViewModel
import com.pavellukyanov.themartian.utils.Constants.Companion.GRID_COLUMNS

class FragmentFavourites : Fragment(R.layout.fragment_favourites) {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private val exchangeViewModel: ExchangeViewModel by activityViewModels()
    private val favouritesAdapter by lazy {
        FavouritesAdapter(
            listOf(),
            deleteFavouriteOnClickListener,
            itemClickListener
        )
    }
    private var favouritesList = mutableListOf<Photo>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouritesBinding.bind(view)
        initRecycler()
        subscribeFavouritesData()
    }

    private fun subscribeFavouritesData() {
        favouritesViewModel.getAllFavourites().observe(viewLifecycleOwner, { onStateReceiveAllFavouritePhoto(it) })
    }

    private fun exchangeCameraAndRoverNameInformation() {
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

    private fun onStateReceiveAllFavouritePhoto(resourceState: ResourceState<List<Photo>>) {
        when (resourceState) {
            is ResourceState.Success -> handleSuccessStateAllPhoto(resourceState.data)
            is ResourceState.Loading -> handleLoadingStateAllPhoto(true)
            is ResourceState.Error -> handleErrorStateAllPhoto(resourceState.error)
        }
    }

    private fun handleSuccessStateAllPhoto(listPhoto: List<Photo>) {
        handleLoadingStateAllPhoto(false)
        favouritesList = listPhoto.toMutableList()
        exchangeViewModel.selectFavouritesCameras(
            convertRoverCameraToHashSet(listPhoto)
        )
        exchangeViewModel.selectRovers(
            convertRoverNameToHashSet(listPhoto)
        )
        exchangeCameraAndRoverNameInformation()
        retrieveList(favouritesList)
    }

    private fun handleLoadingStateAllPhoto(state: Boolean) {

    }

    private fun handleErrorStateAllPhoto(error: Throwable?) {
        handleLoadingStateAllPhoto(false)
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.error_toast, error?.localizedMessage),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun initRecycler() {
        binding.rvFavourites.apply {
            adapter = favouritesAdapter
            layoutManager = GridLayoutManager(context, GRID_COLUMNS)
        }
    }

    private fun convertRoverCameraToHashSet(listPhoto: List<Photo>): HashSet<String> {
        val hashSet: HashSet<String> = hashSetOf()
        listPhoto.forEach {
            hashSet.add(it.camera)
        }
        return hashSet
    }

    private fun convertRoverNameToHashSet(listPhoto: List<Photo>): HashSet<String> {
        val hashSet: HashSet<String> = hashSetOf()
        listPhoto.forEach {
            hashSet.add(it.rover)
        }
        return hashSet
    }

    private val deleteFavouriteOnClickListener = object : DeleteFavouriteOnClickListener {
        override fun deleteFavouriteOnClicked(photo: Photo) {
            favouritesViewModel.deletePhoto(photo)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        favouritesViewModel.onDestroy()
    }
}