package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.databinding.ContentFloatingBinding
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
    private var fabIsOpen = false

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

        //FAB Setting
        val fabClose: Animation = AnimationUtils.loadAnimation(context, R.anim.fab_close)
        val fabOpen: Animation = AnimationUtils.loadAnimation(context, R.anim.fab_open)
        val fabRotateAnticlock: Animation = AnimationUtils.loadAnimation(context, R.anim.fab_rotate_anticlock)
        val fabRotateClock: Animation = AnimationUtils.loadAnimation(context, R.anim.fab_rotate_clock)

        binding.fabSetting.setOnClickListener {
            if (fabIsOpen) {
                if (viewPager.currentItem == 1) {
                    binding.tvRover.visibility = View.INVISIBLE
                    binding.fabRover.startAnimation(fabClose)
                    binding.fabRover.isClickable = false
                }
                binding.tvDate.visibility = View.INVISIBLE
                binding.tvCamera.visibility = View.INVISIBLE
                binding.fabCamera.startAnimation(fabClose)
                binding.fabDate.startAnimation(fabClose)
                binding.fabSetting.startAnimation(fabRotateAnticlock)
                binding.fabCamera.isClickable = false
                binding.fabDate.isClickable = false
                fabIsOpen = false
            } else {
                if (viewPager.currentItem == 1) {
                    binding.tvRover.visibility = View.VISIBLE
                    binding.fabRover.startAnimation(fabOpen)
                    binding.fabRover.isClickable = true
                }
                binding.tvDate.visibility = View.VISIBLE
                binding.tvCamera.visibility = View.VISIBLE
                binding.fabCamera.startAnimation(fabOpen)
                binding.fabDate.startAnimation(fabOpen)
                binding.fabSetting.startAnimation(fabRotateClock)
                binding.fabCamera.isClickable = true
                binding.fabDate.isClickable = true
                fabIsOpen = true
            }
        }

        binding.fabDate.setOnClickListener {
            Toast.makeText(context, "Date", Toast.LENGTH_SHORT).show()
        }

        binding.fabCamera.setOnClickListener {
            Toast.makeText(context, "Camera", Toast.LENGTH_SHORT).show()
        }

        binding.fabRover.setOnClickListener {
            Toast.makeText(context, "Rover", Toast.LENGTH_SHORT).show()
        }
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