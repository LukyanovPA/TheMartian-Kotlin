package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.api.ApiHelper
import com.pavellukyanov.themartian.data.api.GoRetrofit
import com.pavellukyanov.themartian.data.model.Photo
import com.pavellukyanov.themartian.ui.base.ViewModelFactory
import com.pavellukyanov.themartian.ui.main.adapter.GalleryAdapter
import com.pavellukyanov.themartian.ui.main.viewmodel.MainVewModel
import com.pavellukyanov.themartian.utils.Status
import kotlinx.android.synthetic.main.fragment_photo_gallery.*
import java.util.EnumSet.of

class PhotoGalleryFragment : Fragment(R.layout.fragment_photo_gallery) {

    private lateinit var viewModel: MainVewModel
    private lateinit var adapter: GalleryAdapter
    private val roverName = "curiosity"
    private val sol = 1650L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory(ApiHelper(GoRetrofit.apiService))
        viewModel = ViewModelProvider(this, factory).get(MainVewModel::class.java)
    }

    private fun setupUI() {
        rvPhotoGallery.layoutManager = GridLayoutManager(context, 2)
        adapter = GalleryAdapter(arrayListOf())
        rvPhotoGallery.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getPhotos(roverName, sol).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        rvPhotoGallery.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        //для теста
                        val ms = resource.data
                        Log.d("myRt", ms.toString())
                        resource.data?.let { mars -> retrieveList(mars.photos) }
                    }
                    Status.ERROR -> {
                        rvPhotoGallery.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        Log.d("stater", it.message.toString())
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        rvPhotoGallery.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(photos: List<Photo>) {
        Log.d("stater", photos.toString())
        adapter.apply {
            addPhotos(photos)
            notifyDataSetChanged()
        }
    }
}