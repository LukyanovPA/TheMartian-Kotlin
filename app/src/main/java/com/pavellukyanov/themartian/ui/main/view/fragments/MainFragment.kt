package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.api.ApiManifestHelper
import com.pavellukyanov.themartian.data.api.GoRetrofit
import com.pavellukyanov.themartian.data.model.RoverInfo
import com.pavellukyanov.themartian.ui.base.ManifestViewModFactory
import com.pavellukyanov.themartian.ui.main.adapter.MainAdapter
import com.pavellukyanov.themartian.ui.main.viewmodel.ManifestViewModel
import com.pavellukyanov.themartian.utils.Status
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var vmCuri: ManifestViewModel
    private lateinit var vmOppo: ManifestViewModel
    private lateinit var vmSpirit: ManifestViewModel

    private lateinit var resultList: MutableList<RoverInfo>
    private lateinit var adapter: MainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel()
        goManifest()
    }

    private fun setupUI() {
        mainRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        Log.d(LOG_TAG, "setupUI list - ${resultList.size}")
        adapter = MainAdapter(resultList)
        mainRecycler.adapter = adapter
        mainRecycler.visibility = View.VISIBLE
    }

    private fun getViewModel() {
        val testFactory = ManifestViewModFactory(ApiManifestHelper(GoRetrofit.apiManifestService))
        vmCuri = ViewModelProvider(this, testFactory).get(ManifestViewModel::class.java)
        vmOppo = ViewModelProvider(this, testFactory).get(ManifestViewModel::class.java)
        vmSpirit = ViewModelProvider(this, testFactory).get(ManifestViewModel::class.java)
    }

    private fun goManifest() {
        getObservers()
        Log.d(LOG_TAG, "$vmCuri, $vmOppo, $vmSpirit")
        Log.d(LOG_TAG, "goManifest тут лист - ${resultList.size}")

    }

    private fun getObservers() {
        resultList = mutableListOf()
        vmCuri.getRoverManifest(CURIOSITY).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { rover ->
                            rover.photoManifest.picture = R.drawable.curiosity
                            val rovCuri: RoverInfo = rover.photoManifest
                            resultList.add(rovCuri)
                            Log.d(LOG_TAG, "1 observ тут лист - ${resultList.size}")
                        }
                        getOppo()
                        Log.d(LOG_TAG, "Good")
                    }
                    Status.ERROR -> {
                        Log.d(LOG_TAG, "ERROR no good")
                    }
                    Status.LOADING -> {
                        Log.d(LOG_TAG, "Loading")
                    }
                }
            }
        })
    }

    private fun getOppo() {
        vmOppo.getRoverManifest(OPPORTUNITY).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { rover ->
                            rover.photoManifest.picture = R.drawable.opportunity
                            val rovOpp: RoverInfo = rover.photoManifest
                            resultList.add(rovOpp)
                            Log.d(LOG_TAG, "2 observ тут лист - ${resultList.size}")
                        }
                        getSpirit()
                        Log.d(LOG_TAG, "Good")
                    }
                    Status.ERROR -> {
                        Log.d(LOG_TAG, "ERROR no good")
                    }
                    Status.LOADING -> {
                        Log.d(LOG_TAG, "Loading")
                    }
                }
            }
        })
    }

    private fun getSpirit() {
        vmSpirit.getRoverManifest(SPIRIT).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { rover ->
                            rover.photoManifest.picture = R.drawable.spirit
                            val rovSpir: RoverInfo = rover.photoManifest
                            resultList.add(rovSpir)
                            Log.d(LOG_TAG, "3 observ тут лист - ${resultList.size}")
                            setupUI()
                        }
                        Log.d(LOG_TAG, "Good")
                    }
                    Status.ERROR -> {
                        Log.d(LOG_TAG, "ERROR no good")
                    }
                    Status.LOADING -> {
                        Log.d(LOG_TAG, "Loading")
                    }
                }
            }
        })
    }

    companion object {
        private const val LOG_TAG = "roversInfo"
        private const val CURIOSITY = "Curiosity"
        private const val OPPORTUNITY = "Opportunity"
        private const val SPIRIT = "Spirit"
    }
}