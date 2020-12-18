package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.api.ApiManifestHelper
import com.pavellukyanov.themartian.data.api.GoRetrofit
import com.pavellukyanov.themartian.data.model.RoverInfo
import com.pavellukyanov.themartian.ui.base.ManifestViewModFactory
import com.pavellukyanov.themartian.ui.main.adapter.ItemClickListener
import com.pavellukyanov.themartian.ui.main.adapter.LinePagerIndicatorDecoration
import com.pavellukyanov.themartian.ui.main.adapter.MainAdapter
import com.pavellukyanov.themartian.ui.main.viewmodel.ManifestViewModel
import com.pavellukyanov.themartian.utils.Status
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var vmCuriosity: ManifestViewModel
    private lateinit var vmOpportunity: ManifestViewModel
    private lateinit var vmSpirit: ManifestViewModel
    private lateinit var resultList: MutableList<RoverInfo>
    private lateinit var adapter: MainAdapter

    private var tCuriosity = RoverInfo(
        R.drawable.curiosity_rover,
        R.drawable.curiosity,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

    private var tOpp = RoverInfo(
        R.drawable.opportunity_rover,
        R.drawable.opportunity,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

    private var tSpirit = RoverInfo(
        R.drawable.spirit_rover,
        R.drawable.spirit,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModels()
        setupUI()
        setupData()
    }

    private fun initViewModels() {
        val factory = ManifestViewModFactory(ApiManifestHelper(GoRetrofit.apiManifestService))
        vmCuriosity = ViewModelProvider(this, factory).get(ManifestViewModel::class.java)
        vmOpportunity = ViewModelProvider(this, factory).get(ManifestViewModel::class.java)
        vmSpirit = ViewModelProvider(this, factory).get(ManifestViewModel::class.java)
    }

    private fun setupData() {
        resultList = mutableListOf()
        getCuriosity(tCuriosity)
        getOppo(tOpp)
        getSpirit(tSpirit)
    }

    private fun setListRovInfo(rovInfo: RoverInfo) {
        resultList.add(rovInfo)
    }

    private fun getCuriosity(rovInfo: RoverInfo) {
        vmCuriosity.getRoverManifest(CURIOSITY).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        mainRecycler.visibility = View.VISIBLE
                        progressBar2.visibility = View.GONE
                        resource.data?.let { roverInfo ->
                            addRovInfo(rovInfo, roverInfo.photoManifest)
                            setListRovInfo(rovInfo)
                            if (resultList.size == 3) {
                                retrieveList(resultList)
                            }
                        }
                    }
                    Status.ERROR -> {
                        mainRecycler.visibility = View.VISIBLE
                        progressBar2.visibility = View.GONE
                        Toast.makeText(
                            context,
                            getString(R.string.toast_error_message),
                            Toast.LENGTH_LONG
                        ).show()

                        //Log
                        Log.d(LOG_TAG, it.message.toString())
                    }
                    Status.LOADING -> {
                        progressBar2.visibility = View.VISIBLE
                        mainRecycler.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun getOppo(rovInfo: RoverInfo) {
        vmOpportunity.getRoverManifest(OPPORTUNITY).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        mainRecycler.visibility = View.VISIBLE
                        progressBar2.visibility = View.GONE
                        resource.data?.let { roverInfo ->
                            addRovInfo(rovInfo, roverInfo.photoManifest)
                            setListRovInfo(rovInfo)
                            if (resultList.size == 3) {
                                retrieveList(resultList)
                            }
                        }
                    }
                    Status.ERROR -> {
                        mainRecycler.visibility = View.VISIBLE
                        progressBar2.visibility = View.GONE
                        Toast.makeText(
                            context,
                            getString(R.string.toast_error_message),
                            Toast.LENGTH_LONG
                        ).show()

                        //Log
                        Log.d(LOG_TAG, it.message.toString())
                    }
                    Status.LOADING -> {
                        progressBar2.visibility = View.VISIBLE
                        mainRecycler.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun getSpirit(rovInfo: RoverInfo) {
        vmSpirit.getRoverManifest(SPIRIT).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        mainRecycler.visibility = View.VISIBLE
                        progressBar2.visibility = View.GONE
                        resource.data?.let { roverInfo ->
                            addRovInfo(rovInfo, roverInfo.photoManifest)
                            setListRovInfo(rovInfo)
                            if (resultList.size == 3) {
                                retrieveList(resultList)
                            }
                        }
                    }
                    Status.ERROR -> {
                        mainRecycler.visibility = View.VISIBLE
                        progressBar2.visibility = View.GONE
                        Toast.makeText(
                            context,
                            getString(R.string.toast_error_message),
                            Toast.LENGTH_LONG
                        ).show()

                        //Log
                        Log.d(LOG_TAG, it.message.toString())
                    }
                    Status.LOADING -> {
                        progressBar2.visibility = View.VISIBLE
                        mainRecycler.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun addRovInfo(rovIn: RoverInfo, rovOut: RoverInfo) {
        rovIn.name = rovOut.name
        rovIn.landingDate = rovOut.landingDate
        rovIn.launchData = rovOut.launchData
        rovIn.status = rovOut.status
        rovIn.maxSol = rovOut.maxSol
        rovIn.maxDate = rovOut.maxDate
        rovIn.totalPhotos = rovOut.totalPhotos
        rovIn.photos = rovOut.photos
    }

    private fun setupUI() {
        mainRecycler.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        mainRecycler.addItemDecoration(LinePagerIndicatorDecoration())
        adapter = MainAdapter(arrayListOf(), clickListener)
        mainRecycler.adapter = adapter
    }

    private fun retrieveList(roversInfo: MutableList<RoverInfo>) {
        adapter.apply {
            addRoversInfo(roversInfo)
            notifyDataSetChanged()
        }
    }

    val clickListener = object : ItemClickListener {
        override fun onItemClicked(roverInfo: RoverInfo) {
            showRoverDetailsFragment(roverInfo)
        }
    }

    private fun showRoverDetailsFragment(roverInfo: RoverInfo) {
        requireFragmentManager().beginTransaction()
            .replace(R.id.flMainFragmetn, FragmentRoverDetails.newInstance(roverInfo))
            .addToBackStack("FragmentRoverDetails")
            .commit()
    }

    companion object {
        private const val LOG_TAG = "roversInfo"
        private const val CURIOSITY = "Curiosity"
        private const val OPPORTUNITY = "Opportunity"
        private const val SPIRIT = "Spirit"
    }
}
