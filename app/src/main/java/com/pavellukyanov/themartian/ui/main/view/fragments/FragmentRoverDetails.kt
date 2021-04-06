package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.api.Router
import com.pavellukyanov.themartian.data.api.models.Photo
import com.pavellukyanov.themartian.data.api.models.RoverInfo
import com.pavellukyanov.themartian.ui.base.RoverDetailsViewModelFactory
import com.pavellukyanov.themartian.ui.main.adapter.GalleryAdapter
import com.pavellukyanov.themartian.ui.main.viewmodel.RoverDetailsViewModel
import com.pavellukyanov.themartian.utils.Status
import kotlinx.android.synthetic.main.fragment_rover_details.*

class FragmentRoverDetails : Fragment(R.layout.fragment_rover_details) {
    private val roverDetailsViewModel: RoverDetailsViewModel by viewModels {
        RoverDetailsViewModelFactory(Router.apiService)
    }
    private lateinit var adapter: GalleryAdapter
    private lateinit var roverInfo: RoverInfo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roverInfo = requireNotNull(requireArguments()).getParcelable(ROVER_KEY)!!
        setupUI(roverInfo)
        getData(roverInfo)
    }

    private fun setupUI(roverInfo: RoverInfo) {
        rvDetails.layoutManager = GridLayoutManager(context, 2)
        adapter = GalleryAdapter(arrayListOf())
        rvDetails.adapter = adapter

        buttonBack.setOnClickListener { activity?.onBackPressed() }
        roverDetailsName.text = roverInfo.name
        detailsLaunchDate.text = roverInfo.launchData
        detailsTotalPhoto.text = roverInfo.totalPhotos.toString()
        detailsLatestPhoto.text = roverInfo.maxDate

        context?.let {
            Glide.with(it)
                .asBitmap()
                .load(
                    when (roverInfo.name) {
                        CURIOSITY -> R.drawable.curiosity_rover
                        OPPORTUNITY -> R.drawable.opportunity_rover
                        SPIRIT -> R.drawable.spirit_rover
                        else -> null
                    }
                )
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .into(detailRoverPicture)
        }
    }

    private fun getData(roverInfo: RoverInfo) {
        roverDetailsViewModel.getPhotosForEarthData(roverInfo.name, roverInfo.maxDate)
            .observe(this.viewLifecycleOwner, Observer { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        rvDetails.visibility = View.VISIBLE
                        progressBar3.visibility = View.GONE
                        response.data?.let { mars -> retrieveList(mars.photos) }
                    }
                    Status.ERROR -> {
                        rvDetails.visibility = View.VISIBLE
                        progressBar3.visibility = View.GONE
                        Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                        Log.d(LOG_TAG, response.message.toString())
                    }
                    Status.LOADING -> {
                        progressBar3.visibility = View.VISIBLE
                        rvDetails.visibility = View.GONE
                    }
                }
            })
    }

    private fun retrieveList(photos: List<Photo>) {
        adapter.apply {
            addPhotos(photos)
            notifyDataSetChanged()
        }
    }

    companion object {
        private const val ROVER_KEY = "rover"
        private const val LOG_TAG = "roversInfo"
        private const val CURIOSITY = "Curiosity"
        private const val OPPORTUNITY = "Opportunity"
        private const val SPIRIT = "Spirit"

        fun newInstance(roverInfo: RoverInfo): Fragment = FragmentRoverDetails().apply {
            arguments = bundleOf(ROVER_KEY to roverInfo)
        }
    }
}