package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.api.models.Photo
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.databinding.FragmentRoverDetailsBinding
import com.pavellukyanov.themartian.ui.main.adapter.GalleryAdapter
import com.pavellukyanov.themartian.ui.main.viewmodel.RoverDetailsViewModel
import com.pavellukyanov.themartian.utils.Status
import com.pavellukyanov.themartian.utils.Constants.Companion.CURIOSITY
import com.pavellukyanov.themartian.utils.Constants.Companion.OPPORTUNITY
import com.pavellukyanov.themartian.utils.Constants.Companion.SPIRIT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentRoverDetails : Fragment(R.layout.fragment_rover_details) {
    private val args: FragmentRoverDetailsArgs by navArgs()
    private val roverDetailsViewModel: RoverDetailsViewModel by viewModels()
    private lateinit var binding: FragmentRoverDetailsBinding
    private val galleryAdapter by lazy { GalleryAdapter(arrayListOf()) }
    private val roverName by lazy { args.roverInfo }
    private val maxDate by lazy { args.maxDate }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRoverDetailsBinding.bind(view)
        subscribeMarsData(roverName, maxDate)
    }

    private fun setupUI(roverInfo: RoverInfoEntity) {
        binding.rvDetails.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        binding.buttonBack.setOnClickListener { activity?.onBackPressed() }
        binding.roverDetailsName.text = roverInfo.roverName
        binding.detailsLaunchDate.text = roverInfo.launchData
        binding.detailsTotalPhoto.text = roverInfo.totalPhotos.toString()
        binding.detailsLatestPhoto.text = roverInfo.maxDate

        context?.let {
            Glide.with(it)
                .asBitmap()
                .load(
                    when (roverInfo.roverName) {
                        CURIOSITY -> R.drawable.curiosity_rover
                        OPPORTUNITY -> R.drawable.opportunity_rover
                        SPIRIT -> R.drawable.spirit_rover
                        else -> null
                    }
                )
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .into(binding.detailRoverPicture)
        }
    }

    private fun subscribeMarsData(roverName: String, maxDate: String) {
        roverDetailsViewModel.getRoverInfo(roverName).observe(viewLifecycleOwner, { setupUI(it) })
        roverDetailsViewModel.getPhotosForEarthData(roverName, maxDate)
            .observe(viewLifecycleOwner, { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        binding.rvDetails.visibility = View.VISIBLE
                        binding.progressBar3.visibility = View.GONE
                        response.data?.let { mars -> retrieveList(mars.photos) }
                    }
                    Status.ERROR -> {
                        binding.rvDetails.visibility = View.VISIBLE
                        binding.progressBar3.visibility = View.GONE
                        Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar3.visibility = View.VISIBLE
                        binding.rvDetails.visibility = View.GONE
                    }
                }
            })
    }

    private fun retrieveList(photos: List<Photo>) {
        galleryAdapter.apply {
            addPhotos(photos)
            notifyDataSetChanged()
        }
    }
}