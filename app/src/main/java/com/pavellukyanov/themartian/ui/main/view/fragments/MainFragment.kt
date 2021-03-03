package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.api.ApiHelper
import com.pavellukyanov.themartian.data.api.ApiManifestHelper
import com.pavellukyanov.themartian.data.api.Router
import com.pavellukyanov.themartian.data.database.DataBase
import com.pavellukyanov.themartian.data.models.network.RoverInfo
import com.pavellukyanov.themartian.databinding.FragmentMainBinding
import com.pavellukyanov.themartian.ui.base.MainViewModFactory
import com.pavellukyanov.themartian.ui.main.adapter.ItemClickListener
import com.pavellukyanov.themartian.ui.main.adapter.LinePagerIndicatorDecoration
import com.pavellukyanov.themartian.ui.main.adapter.MainAdapter
import com.pavellukyanov.themartian.ui.main.viewmodel.MainViewModel
import com.pavellukyanov.themartian.utils.Status

class MainFragment : Fragment(R.layout.fragment_main) {
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModFactory(
            DataBase.instance,
            ApiManifestHelper(Router.apiManifestService),
            ApiHelper(Router.apiService)
        )
    }
    private val binding: FragmentMainBinding by lazy { FragmentMainBinding.inflate(this.layoutInflater) }
    private lateinit var adapter: MainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeData()
        initAdapter()
    }

    private fun subscribeData() {
        mainViewModel.getRoverManifest().observe(this.viewLifecycleOwner, Observer { status ->
                when(status.status) {
                    Status.SUCCESS -> {
                        binding.mainRecycler.visibility = View.VISIBLE
                        binding.progressBar2.visibility = View.GONE
                        Log.d("ttt", "Status - ${status.data?.size}")
                        if (status.data != null) { retrieveList(status.data)  }
                    }
                }
        })
    }

    private fun initAdapter() {
        binding.mainRecycler.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        binding.mainRecycler.addItemDecoration(LinePagerIndicatorDecoration())
        adapter = MainAdapter(arrayListOf(), clickListener)
        binding.mainRecycler.adapter = adapter
    }

    private fun retrieveList(roversInfo: List<RoverInfo>) {
        Log.d("ttt", "InitAdapter - ${roversInfo[0].maxDate}")
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
