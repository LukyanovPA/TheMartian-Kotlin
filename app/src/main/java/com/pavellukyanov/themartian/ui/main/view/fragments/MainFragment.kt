package com.pavellukyanov.themartian.ui.main.view.fragments

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
import com.pavellukyanov.themartian.data.model.Photo
import com.pavellukyanov.themartian.data.model.RoverInfo
import com.pavellukyanov.themartian.ui.base.ManifestViewModFactory
import com.pavellukyanov.themartian.ui.main.adapter.MainAdapter
import com.pavellukyanov.themartian.ui.main.buisneslogics.RoversInfoList
import com.pavellukyanov.themartian.ui.main.viewmodel.ManifestViewModel
import com.pavellukyanov.themartian.utils.Status
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_photo_gallery.*
import kotlinx.coroutines.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var vmCuriosity: ManifestViewModel
    private lateinit var vmOpportunity: ManifestViewModel
    private lateinit var vmSpirit: ManifestViewModel
    private lateinit var resultList: MutableList<RoverInfo>
    private lateinit var adapter: MainAdapter

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    private var scope = CoroutineScope(
        SupervisorJob() +
                Dispatchers.Main +
                exceptionHandler
    )

    override fun onStart() {
        super.onStart()
        initViewModels()
        setupUI()
        scope.launch { setupData() }
    }

    private fun initViewModels() {
        val factory = ManifestViewModFactory(ApiManifestHelper(GoRetrofit.apiManifestService))
        vmCuriosity = ViewModelProvider(this, factory).get(ManifestViewModel::class.java)
        vmOpportunity = ViewModelProvider(this, factory).get(ManifestViewModel::class.java)
        vmSpirit = ViewModelProvider(this, factory).get(ManifestViewModel::class.java)
    }

    private suspend fun setupData() {
        resultList = mutableListOf()
        scope.async { getCuriosity() }.await()

    }

    private fun setListRovInfo(rovInfo: RoverInfo) {
        resultList.add(rovInfo)
    }

    private fun getCuriosity() {
        vmCuriosity.getRoverManifest(CURIOSITY).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        mainRecycler.visibility = View.VISIBLE
                        progressBar2.visibility = View.GONE
                        resource.data?.let { roverInfo ->
                            setListRovInfo(roverInfo.photoManifest)
                            getOppo()
                        }
                    }
                    Status.ERROR -> {
                        mainRecycler.visibility = View.VISIBLE
                        progressBar2.visibility = View.GONE

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

    private fun getOppo() {
        vmOpportunity.getRoverManifest(OPPORTUNITY).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        mainRecycler.visibility = View.VISIBLE
                        progressBar2.visibility = View.GONE
                        resource.data?.let { roverInfo ->
                            setListRovInfo(roverInfo.photoManifest)
                            getSpirit()
                        }
                    }
                    Status.ERROR -> {
                        mainRecycler.visibility = View.VISIBLE
                        progressBar2.visibility = View.GONE

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

    private fun getSpirit() {
        vmSpirit.getRoverManifest(SPIRIT).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        mainRecycler.visibility = View.VISIBLE
                        progressBar2.visibility = View.GONE
                        resource.data?.let { roverInfo ->
                            setListRovInfo(roverInfo.photoManifest)
                            retrieveList(resultList)
                        }
                    }
                    Status.ERROR -> {
                        mainRecycler.visibility = View.VISIBLE
                        progressBar2.visibility = View.GONE

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

    private fun setupUI() {
        mainRecycler.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        adapter = MainAdapter(arrayListOf())
        mainRecycler.adapter = adapter
    }

    private fun retrieveList(roversInfo: MutableList<RoverInfo>) {
        roversInfo.forEach {
            when (it.name) {
                "Curiosity" -> it.picture = R.drawable.curiosity
                "Opportunity" -> it.picture = R.drawable.opportunity
                "Spirit" -> it.picture = R.drawable.spirit
            }
        }
        adapter.apply {
            addRoversInfo(roversInfo)
            notifyDataSetChanged()
        }
    }

    companion object {
        private const val LOG_TAG = "roversInfo"
        private const val CURIOSITY = "Curiosity"
        private const val OPPORTUNITY = "Opportunity"
        private const val SPIRIT = "Spirit"
    }
}