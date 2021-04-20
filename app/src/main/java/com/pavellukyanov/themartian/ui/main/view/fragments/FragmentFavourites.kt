package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.domain.DomainPhoto
import com.pavellukyanov.themartian.databinding.FragmentFavouritesBinding
import com.pavellukyanov.themartian.ui.main.adapters.DeleteFavouriteOnClickListener
import com.pavellukyanov.themartian.ui.main.adapters.FavouritesAdapter
import com.pavellukyanov.themartian.ui.main.viewmodel.ExchangeViewModel
import com.pavellukyanov.themartian.ui.main.viewmodel.FavouritesViewModel
import com.pavellukyanov.themartian.utils.Constants.Companion.GRID_COLUMNS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentFavourites : Fragment(R.layout.fragment_favourites) {
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private val exchangeViewModel: ExchangeViewModel by activityViewModels()
    private val favouritesAdapter by lazy { FavouritesAdapter(listOf(), deleteFavouriteOnClickListener) }
    private lateinit var binding: FragmentFavouritesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouritesBinding.bind(view)
        setupIU()
        subscribeFavouritesData()
    }

    private fun subscribeFavouritesData() {
        favouritesViewModel.getAllFavourites().observe(viewLifecycleOwner, { listPhoto ->
            retrieveList(listPhoto)
            val cameras: HashSet<String> = hashSetOf()
            listPhoto.forEach {
                cameras.add(it.camera)
            }
            Log.d("ttt", "cameras - ${cameras.size}")
            exchangeViewModel.selectFavouritesCameras(cameras)
        })
        exchangeViewModel.returnChooseFavCam().observe(viewLifecycleOwner, {
            Log.d("ttt", "choose - ${it.size}")
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
            Snackbar.make(binding.scrollLayoutFav, getString(R.string.snack_delete), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun retrieveList(list: List<DomainPhoto>) {
        favouritesAdapter.apply {
            addPhotos(list)
        }
    }
}