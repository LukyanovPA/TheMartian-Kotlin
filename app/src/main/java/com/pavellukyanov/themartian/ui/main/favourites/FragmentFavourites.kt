package com.pavellukyanov.themartian.ui.main.favourites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.databinding.FragmentFavouritesBinding
import com.pavellukyanov.themartian.domain.photo.Photo
import com.pavellukyanov.themartian.ui.base.BaseFragment
import com.pavellukyanov.themartian.ui.base.DeleteFavouriteOnClickListener
import com.pavellukyanov.themartian.ui.main.favourites.adapter.FavouritesAdapter
import com.pavellukyanov.themartian.ui.base.ItemClickListener
import com.pavellukyanov.themartian.ui.main.viewmodel.ExchangeViewModel
import com.pavellukyanov.themartian.utils.Constants.Companion.GRID_COLUMNS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentFavourites : BaseFragment<List<Photo>, FavouritesViewModel>(R.layout.fragment_favourites) {
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
        favouritesViewModel.getAllFavourites().observe(viewLifecycleOwner, (::onStateReceive))
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

    override fun handleSuccessState(data: List<Photo>) {
        super.handleSuccessState(data)
        favouritesList = data.toMutableList()
        exchangeViewModel.selectFavouritesCameras(
            convertRoverCameraToHashSet(data)
        )
        exchangeViewModel.selectRovers(
            convertRoverNameToHashSet(data)
        )
        exchangeCameraAndRoverNameInformation()
        retrieveList(favouritesList)
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