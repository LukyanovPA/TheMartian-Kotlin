package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.api.ApiManifestHelper
import com.pavellukyanov.themartian.data.api.GoRetrofit
import com.pavellukyanov.themartian.data.database.DataBase
import com.pavellukyanov.themartian.data.models.dbmodel.RoverInfoEntity
import com.pavellukyanov.themartian.data.models.networkmodel.RoverInfo
import com.pavellukyanov.themartian.databinding.FragmentTestBinding
import com.pavellukyanov.themartian.ui.base.ManifestViewModFactory
import com.pavellukyanov.themartian.ui.base.TestViewModelFactory
import com.pavellukyanov.themartian.ui.main.adapter.ItemClickListener
import com.pavellukyanov.themartian.ui.main.adapter.LinePagerIndicatorDecoration
import com.pavellukyanov.themartian.ui.main.adapter.MainAdapter
import com.pavellukyanov.themartian.ui.main.adapter.NewItemClickListener
import com.pavellukyanov.themartian.ui.main.viewmodel.ManifestViewModel
import com.pavellukyanov.themartian.ui.main.viewmodel.TestViewModel
import com.pavellukyanov.themartian.utils.Status

class FragmentTest : Fragment(R.layout.fragment_test) {
    private val testViewModel: TestViewModel by viewModels {
        TestViewModelFactory(
            DataBase.instance,
            ApiManifestHelper(GoRetrofit.apiManifestService)
        )
    }
    private val binding: FragmentTestBinding by lazy {
        FragmentTestBinding.inflate(this.layoutInflater)
    }
    private lateinit var adapter: MainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeData()
    }

    private fun subscribeData() {
        testViewModel.getRoverManifest().observe(this.viewLifecycleOwner, { res ->
            Log.d("ttt", "Observ - ${res.size}")
            binding.mainRecycler.visibility = View.VISIBLE
            binding.progressBar2.visibility = View.GONE
            initViews(res)
//            when (res.status) {
//                Status.SUCCESS -> {
//                    binding.mainRecycler.visibility = View.VISIBLE
//                    binding.progressBar2.visibility = View.GONE
//                    retrieveList(res.data?.value)
//                }
//                Status.ERROR -> {
//                    binding.mainRecycler.visibility = View.VISIBLE
//                    binding.progressBar2.visibility = View.GONE
//                    Toast.makeText(
//                        context,
//                        getString(R.string.toast_error_message),
//                        Toast.LENGTH_LONG
//                    ).show()
//
//                    //Log
//                    Log.d("ttt", "Fragment - ${res.message}")
//                }
//                Status.LOADING -> {
//                    binding.progressBar2.visibility = View.VISIBLE
//                    binding.mainRecycler.visibility = View.GONE
//                }
//            }
        })
    }

    private fun initViews(list: List<RoverInfoEntity>) {
        binding.mainRecycler.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        binding.mainRecycler.addItemDecoration(LinePagerIndicatorDecoration())
        adapter = MainAdapter(list, clickListener)
        binding.mainRecycler.adapter = adapter
    }

//    private fun retrieveList(roversInfo: List<RoverInfoEntity>) {
//        Log.d("ttt", "Enter fun rec - ${roversInfo?.get(0)?.name}")
//        val rovRov = mutableListOf<RoverInfoEntity>()
//        roversInfo?.forEach { rovRov.add(it) }
//        Log.d("ttt", "Set rec - ${rovRov[0].name}")
//        adapter.setItems(roversInfo)
//    }

    val clickListener = object : NewItemClickListener {
        override fun onClicked(roverInfo: RoverInfoEntity) {
//            showRoverDetailsFragment(roverInfo)
        }
    }

    private fun showRoverDetailsFragment(roverInfo: RoverInfo) {
        requireFragmentManager().beginTransaction()
            .replace(R.id.flMainFragmetn, FragmentRoverDetails.newInstance(roverInfo))
            .addToBackStack("FragmentRoverDetails")
            .commit()
    }
}