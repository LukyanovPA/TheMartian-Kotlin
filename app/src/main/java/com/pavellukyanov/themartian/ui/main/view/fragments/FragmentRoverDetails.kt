package com.pavellukyanov.themartian.ui.main.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.api.ApiHelper
import com.pavellukyanov.themartian.data.api.GoRetrofit
import com.pavellukyanov.themartian.data.model.Photo
import com.pavellukyanov.themartian.data.model.RoverInfo
import com.pavellukyanov.themartian.ui.base.ViewModelFactory
import com.pavellukyanov.themartian.ui.main.adapter.GalleryAdapter
import com.pavellukyanov.themartian.ui.main.viewmodel.MainVewModel
import com.pavellukyanov.themartian.utils.Status
import kotlinx.android.synthetic.main.fragment_rover_details.*

class FragmentRoverDetails: Fragment(R.layout.fragment_rover_details) {
    private lateinit var vmRover: MainVewModel
    private lateinit var adapter: GalleryAdapter
    private lateinit var roverInfo: RoverInfo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roverInfo = requireNotNull(requireArguments()).getParcelable(ROVER_KEY)!!
        initViewModel()
        setupUI(roverInfo)
        getData(roverInfo)
    }

    private fun initViewModel() {
        val factory = ViewModelFactory(ApiHelper(GoRetrofit.apiService))
        vmRover = ViewModelProvider(this, factory).get(MainVewModel::class.java)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupUI(roverInfo: RoverInfo) {
        rvDetails.layoutManager = GridLayoutManager(context, 2)
        adapter = GalleryAdapter(arrayListOf())
        rvDetails.adapter = adapter

        buttonBack.setOnClickListener{ activity?.onBackPressed() }
        roverDetailsName.text = roverInfo.name
        detailsLaunchDate.text = roverInfo.launchData
        detailsTotalPhoto.text = roverInfo.totalPhotos.toString()
        detailsLatestPhoto.text = roverInfo.maxDate
        when(roverInfo.name) {
            CURIOSITY -> detailRoverPicture.setImageDrawable(context?.let { getDrawable(it, R.drawable.curiosity_rover) })
            OPPORTUNITY -> detailRoverPicture.setImageDrawable(context?.let { getDrawable(it, R.drawable.opportunity_rover) })
            SPIRIT -> detailRoverPicture.setImageDrawable(context?.let { getDrawable(it, R.drawable.spirit_rover) })
        }
    }

    private fun getData(roverInfo: RoverInfo) {
        roverInfo.maxDate?.let {
            roverInfo.name?.let { it1 ->
                vmRover.getPhotosEarthData(it1, it).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                rvDetails.visibility = View.VISIBLE
                                progressBar3.visibility = View.GONE
                                //для теста
                                val ms = resource.data
                                Log.d(LOG_TAG, ms.toString())
                                resource.data?.let { mars -> retrieveList(mars.photos) }
                            }
                            Status.ERROR -> {
                                rvDetails.visibility = View.VISIBLE
                                progressBar3.visibility = View.GONE
                                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                                Log.d(LOG_TAG, it.message.toString())
                            }
                            Status.LOADING -> {
                                progressBar3.visibility = View.VISIBLE
                                rvDetails.visibility = View.GONE
                            }
                        }
                    }
                })
            }
        }
    }

    private fun retrieveList(photos: List<Photo>) {
        Log.d("stater", photos.toString())
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