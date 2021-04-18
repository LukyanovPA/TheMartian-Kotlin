package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.databinding.FragmentPagerBinding
import com.pavellukyanov.themartian.ui.main.adapters.ViewPageAdapter
import com.pavellukyanov.themartian.ui.main.viewmodel.ExchangeViewModel
import com.pavellukyanov.themartian.ui.main.viewmodel.RoverDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPager : Fragment(R.layout.fragment_pager) {
    private val args: FragmentPagerArgs by navArgs()
    private val roverDetailsViewModel: RoverDetailsViewModel by viewModels()
    private val exchangeViewModel: ExchangeViewModel by activityViewModels()
    private lateinit var binding: FragmentPagerBinding
    private lateinit var pageAdapter: ViewPageAdapter
    private lateinit var viewPager: ViewPager2
    private val roverName by lazy { args.roverName }
    private val photoDate by lazy { args.maxDate }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPagerBinding.bind(view)
        initPageAdapter()
        subscribeMarsData(roverName, photoDate)
    }

    private fun initPageAdapter() {
        viewPager = binding.pager

        val fragmentList = arrayListOf(
            FragmentRoverDetails(),
            FragmentFavourites()
        )

        pageAdapter = ViewPageAdapter(
            requireContext(),
            fragmentList,
            childFragmentManager,
            viewLifecycleOwner.lifecycle
        )

        binding.pager.adapter = pageAdapter

        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.photo_list)
                1 -> tab.text = getString(R.string.favourites_title)
            }
        }.attach()
    }

    private fun setupUI(roverInfo: RoverInfoEntity) {
        binding.buttonBack.setOnClickListener { activity?.onBackPressed() }
        binding.roverDetailsName.text = roverInfo.roverName
        binding.detailsLaunchDate.text = roverInfo.launchData
        binding.detailsTotalPhoto.text = roverInfo.totalPhotos
        binding.detailsLatestPhoto.text = roverInfo.maxDate
    }

    private fun subscribeMarsData(roverName: String, photoDate: String) {
        roverDetailsViewModel.getRoverInfo(roverName).observe(viewLifecycleOwner, { setupUI(it) })
        roverDetailsViewModel.getPhotosForEarthData(roverName, photoDate)
            .observe(viewLifecycleOwner, { response ->
                Log.d("ttt", "pager ${response.size}")
                exchangeViewModel.selectListPhoto(response)
            })

        exchangeViewModel.getPhotoToFavourite().observe(viewLifecycleOwner, {
            roverDetailsViewModel.addPhotoToFavourite(it)
        })
    }
}