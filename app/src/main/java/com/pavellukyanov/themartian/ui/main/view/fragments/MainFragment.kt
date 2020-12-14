package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.api.ApiManifestHelper
import com.pavellukyanov.themartian.data.api.GoRetrofit
import com.pavellukyanov.themartian.data.model.Rover
import com.pavellukyanov.themartian.data.model.RoverInfo
import com.pavellukyanov.themartian.data.model.RoverManifest
import com.pavellukyanov.themartian.ui.base.ManifestViewModFactory
import com.pavellukyanov.themartian.ui.main.viewmodel.ManifestViewModel
import com.pavellukyanov.themartian.utils.Status
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    //test val
    private lateinit var manifViewModel: ManifestViewModel
    private val roverName = "Curiosity"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //test fun
        testManifest()
        testObserv()
    }

    //test fun
    private fun testManifest() {
        val testFactory = ManifestViewModFactory(ApiManifestHelper(GoRetrofit.apiManifestService))
        manifViewModel = ViewModelProvider(this, testFactory).get(ManifestViewModel::class.java)
    }

    //test fun
    private fun setManif(rover: RoverInfo) {
        testTV.text = rover.name
    }

    //test fun
    private fun testObserv() {
        manifViewModel.getRoverManifest(roverName).observe(this, {
            it?.let { resource ->
                when(resource.status) {
                    Status.SUCCESS -> {
                        testTV.text = resource.data?.photoManifest?.name
                        resource.data?.let { rover -> setManif(rover.photoManifest) }
                        Log.d("testManif", "Good")
                    }
                    Status.ERROR -> {
                        testTV.text = "Не пришло"
                        Log.d("testManif", "ERROR no good")
                    }
                    Status.LOADING -> {
                        Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}