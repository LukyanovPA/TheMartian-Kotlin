package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.api.models.Photo
import com.pavellukyanov.themartian.databinding.FragmentRoverDetailsBinding
import com.pavellukyanov.themartian.ui.main.adapters.GalleryAdapter
import com.pavellukyanov.themartian.ui.main.viewmodel.ExchangeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentRoverDetails : Fragment(R.layout.fragment_rover_details) {
    private val exchangeViewModel: ExchangeViewModel by activityViewModels()
    private lateinit var binding: FragmentRoverDetailsBinding
    private val galleryAdapter by lazy { GalleryAdapter(arrayListOf()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRoverDetailsBinding.bind(view)
        setupUI()
        subscribeExchangeData()
    }

    private fun setupUI() {
        binding.rvDetails.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun subscribeExchangeData() {
        exchangeViewModel.returnListPhoto().observe(viewLifecycleOwner, { retrieveList(it) })
    }

    private fun retrieveList(photos: List<Photo>) {
        galleryAdapter.apply {
            addPhotos(photos)
        }
    }
}