package com.pavellukyanov.themartian.ui.main.view.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.databinding.FragmentPagerBinding
import com.pavellukyanov.themartian.ui.main.adapters.ViewPageAdapter
import com.pavellukyanov.themartian.ui.main.viewmodel.ExchangeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class FragmentPager : Fragment(R.layout.fragment_pager) {
    private val args: FragmentPagerArgs by navArgs()
    private val exchangeViewModel: ExchangeViewModel by activityViewModels()
    private lateinit var binding: FragmentPagerBinding
    private lateinit var pageAdapter: ViewPageAdapter
    private lateinit var viewPager: ViewPager2
    private val roverName by lazy { args.roverInfoEntity.roverName }
    private val photoDate by lazy { args.roverInfoEntity.maxDate }
    private val minDate by lazy { args.roverInfoEntity.landingDate }
    private var fabIsOpen = false
    private var networkCameras = arrayListOf<String>()
    private var favouritesCameras = arrayListOf<String>()
    private var roversList = arrayListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPagerBinding.bind(view)
        initPageAdapter()
        setupUI(args.roverInfoEntity)
        setupExchangeInformation(roverName, photoDate)
    }

    private fun setupExchangeInformation(roverName: String, date: String) {
        exchangeViewModel.selectActualDate(roverName, date)
        exchangeViewModel.returnNetworkCameras().observe(viewLifecycleOwner, { camResponse ->
            networkCameras.clear()
            camResponse.forEach { networkCameras.add(it) }
        })
        exchangeViewModel.returnFavouritesCameras().observe(viewLifecycleOwner, { camResponse ->
            favouritesCameras.clear()
            camResponse.forEach { favouritesCameras.add(it) }
        })
        exchangeViewModel.returnRovers().observe(viewLifecycleOwner, { roverResponse ->
            roversList.clear()
            roverResponse.forEach { roversList.add(it) }
        })
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

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (binding.tvCamera.visibility == View.VISIBLE) {
                    fabIsOpen = true
                    changeFab(fabIsOpen)
                }
            }
        })
    }

    private fun setupUI(roverInfo: RoverInfoEntity) {
        binding.buttonBack.setOnClickListener { activity?.onBackPressed() }
        binding.roverDetailsName.text = roverInfo.roverName
        binding.detailsLaunchDate.text = roverInfo.launchData
        binding.detailsTotalPhoto.text = roverInfo.totalPhotos
        binding.detailsLatestPhoto.text = roverInfo.maxDate

        binding.fabSetting.setOnClickListener {
            changeFab(fabIsOpen)
        }

        binding.fabDate.setOnClickListener {
            chooseDate()
        }

        binding.fabCamera.setOnClickListener {
            var cameras: Array<CharSequence> = arrayOf()
            when (viewPager.currentItem) {
                0 -> {
                    cameras.dropWhile { true }
                    cameras = networkCameras.toTypedArray()
                }
                1 -> {
                    cameras.dropWhile { true }
                    cameras = favouritesCameras.toTypedArray()
                }
            }
            val checkedIndex = arrayListOf<Int>()
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.choose_a_camera)
            builder.setMultiChoiceItems(cameras, null) { _, which, isChecked ->
                if (isChecked) {
                    checkedIndex.add(which)
                } else {
                    checkedIndex.remove(Integer.valueOf(which))
                }
            }
                .setPositiveButton(R.string.ok_button) { _, id ->
                    val chooseList = mutableListOf<String>()
                    checkedIndex.forEach {
                        chooseList.add(cameras[it].toString())
                    }
                    when (viewPager.currentItem) {
                        0 -> {
                            exchangeViewModel.selectedChooseCam(chooseList)
                        }
                        1 -> {
                            exchangeViewModel.selectedChooseFavCam(chooseList)
                        }
                    }
                    if (binding.tvCamera.visibility == View.VISIBLE) {
                        fabIsOpen = true
                        changeFab(fabIsOpen)
                    }
                }
                .setNeutralButton(getString(R.string.select_all)) { _, which ->
                    val chooseList = mutableListOf<String>()
                    cameras.forEach { _ ->
                        checkedIndex.add(which)
                    }
                    when (viewPager.currentItem) {
                        0 -> {
                            exchangeViewModel.selectedChooseCam(chooseList)
                        }
                        1 -> {
                            exchangeViewModel.selectedChooseFavCam(chooseList)
                        }
                    }
                    if (binding.tvCamera.visibility == View.VISIBLE) {
                        fabIsOpen = true
                        changeFab(fabIsOpen)
                    }
                }
                .setNegativeButton(R.string.cancel_button) { _, _ ->
                    if (binding.tvCamera.visibility == View.VISIBLE) {
                        fabIsOpen = true
                        changeFab(fabIsOpen)
                    }
                }
                .create()
                .show()
        }

        binding.fabRover.setOnClickListener {
            var rovers: Array<CharSequence> = arrayOf()
            rovers.dropWhile { true }
            rovers = roversList.toTypedArray()
            val checkedIndex = arrayListOf<Int>()
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.choose_a_rover)
            builder.setMultiChoiceItems(rovers, null) { _, which, isChecked ->
                if (isChecked) {
                    checkedIndex.add(which)
                } else {
                    checkedIndex.remove(Integer.valueOf(which))
                }
            }
                .setPositiveButton(R.string.ok_button) { _, id ->
                    val chooseList = mutableListOf<String>()
                    checkedIndex.forEach {
                        chooseList.add(rovers[it].toString())
                    }
                    exchangeViewModel.selectChoosedRovers(chooseList)
                    if (binding.tvCamera.visibility == View.VISIBLE) {
                        fabIsOpen = true
                        changeFab(fabIsOpen)
                    }
                }
                .setNegativeButton(R.string.cancel_button, null)
                .create()
                .show()
        }
    }

    private fun chooseDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val maxDate = format.parse(photoDate)
        val minDate = format.parse(minDate)

        val dpd = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            val newDate = "$year-${month + 1}-$dayOfMonth"
            setupExchangeInformation(roverName, newDate)
            fabIsOpen = true
            changeFab(fabIsOpen)
        }, year, month, day)
        dpd.datePicker.maxDate = maxDate.time
        dpd.datePicker.minDate = minDate.time
        dpd.show()
    }

    private fun changeFab(isOpen: Boolean) {
        val fabClose: Animation = AnimationUtils.loadAnimation(context, R.anim.fab_close)
        val fabOpen: Animation = AnimationUtils.loadAnimation(context, R.anim.fab_open)
        val fabRotateAnticlock: Animation =
            AnimationUtils.loadAnimation(context, R.anim.fab_rotate_anticlock)
        val fabRotateClock: Animation =
            AnimationUtils.loadAnimation(context, R.anim.fab_rotate_clock)

        if (isOpen) {
            when (viewPager.currentItem) {
                1 -> {
                    binding.tvRover.visibility = View.INVISIBLE
                    binding.fabRover.startAnimation(fabClose)
                    binding.fabRover.isClickable = false
                }
                0 -> {
                    binding.tvDate.visibility = View.INVISIBLE
                    binding.fabDate.startAnimation(fabClose)
                    binding.fabDate.isClickable = false
                }
            }
            binding.tvCamera.visibility = View.INVISIBLE
            binding.fabCamera.startAnimation(fabClose)
            binding.fabSetting.startAnimation(fabRotateAnticlock)
            binding.fabCamera.isClickable = false
            fabIsOpen = false
        } else {
            when (viewPager.currentItem) {
                1 -> {
                    binding.fabRover.startAnimation(fabOpen)
                    binding.fabRover.isClickable = true
                    binding.tvRover.visibility = View.VISIBLE
                }
                0 -> {
                    binding.fabDate.startAnimation(fabOpen)
                    binding.fabDate.isClickable = true
                    binding.tvDate.visibility = View.VISIBLE
                }
            }
            binding.fabCamera.startAnimation(fabOpen)
            binding.fabSetting.startAnimation(fabRotateClock)
            binding.fabCamera.isClickable = true
            binding.tvCamera.visibility = View.VISIBLE
            fabIsOpen = true
        }
    }
}