package com.pavellukyanov.themartian.ui.main.gallery

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.databinding.FragmentPagerBinding
import com.pavellukyanov.themartian.domain.photo.Photo
import com.pavellukyanov.themartian.ui.base.BaseFragment
import com.pavellukyanov.themartian.ui.main.favourites.DeleteFavouriteOnClickListener
import com.pavellukyanov.themartian.ui.main.gallery.adapter.GalleryAdapter
import com.pavellukyanov.themartian.ui.main.roverdetails.AddFavouriteOnClickListener
import com.pavellukyanov.themartian.ui.main.roverdetails.ItemClickListener
import com.pavellukyanov.themartian.utils.bindGalleryPager
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class FragmentPager : BaseFragment<List<Photo>, GalleryViewModel>(R.layout.fragment_pager) {
    private var _binding: FragmentPagerBinding? = null
    private val binding get() = _binding!!
    private val args: FragmentPagerArgs by navArgs()
    private val viewModel: GalleryViewModel by viewModels()
    private val roverName by lazy { args.roverInfo.roverName }
    private val photoDate by lazy { args.roverInfo.maxDate }
    private val minDate by lazy { args.roverInfo.landingDate }
    private val adapter by lazy {
        GalleryAdapter(
            mutableListOf(),
            addToFavouriteOnClickListener,
            deleteFavouriteOnClickListener,
            itemClickListener
        )
    }

    //    private val exchangeViewModel: ExchangeViewModel by activityViewModels()
    private lateinit var viewPager: ViewPager2

    //    private var networkCameras = arrayListOf<String>()
    private var favouritesCameras = arrayListOf<String>()
    private var roversList = arrayListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPagerBinding.bind(view)
        getNetworkPhotos(roverName, photoDate)
    }

    private fun getNetworkPhotos(roverName: String, date: String) {
        viewModel.doChangePhotoDate(roverName, date)
//        exchangeViewModel.selectActualDate(roverName, date)
//        exchangeViewModel.returnNetworkCameras().observe(viewLifecycleOwner, { camResponse ->
//            networkCameras.clear()
//            camResponse.forEach { networkCameras.add(it) }
//        })
//        exchangeViewModel.returnFavouritesCameras().observe(viewLifecycleOwner, { camResponse ->
//            favouritesCameras.clear()
//            camResponse.forEach { favouritesCameras.add(it) }
//        })
//        exchangeViewModel.returnRovers().observe(viewLifecycleOwner, { roverResponse ->
//            roversList.clear()
//            roverResponse.forEach { roversList.add(it) }
//        })
    }

    override fun handleSuccessState(data: List<Photo>) {
        super.handleSuccessState(data)
        setupUI(data)
    }

    private fun setupUI(listPhoto: List<Photo>) {
        with(binding) {
            pager.bindGalleryPager(
                listPhoto.size,
                requireContext(),
                adapter
            )

            TabLayoutMediator(tabLayout, pager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.photo_list)
                    1 -> tab.text = getString(R.string.favourites_title)
                }
            }.attach()

            pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    if (tvCamera.isVisible) {
                        this@with.changeFab(true, requireContext(), pager)
                    }
                }
            })

            buttonBack.setOnClickListener {
                activity?.onBackPressed()
            }

            fabSetting.setOnClickListener {
                this.changeFab(false, requireContext(), pager)
            }

            fabDate.setOnClickListener {
                //определять где менять дату - нетворк или фэйворит
                getNetworkPhotos(
                    roverName,
                    chooseDate(photoDate, minDate, requireContext())
                )
            }

            fabCamera.setOnClickListener {
                var cameras: Array<CharSequence> = arrayOf()
                when (viewPager.currentItem) {
                    0 -> {
                        cameras.dropWhile { true }
                        cameras = viewModel.availableNetworkCameras().toTypedArray()
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
                                adapter.chooseCamera(chooseList)
                            }
                            1 -> {
                                adapter.chooseCamera(chooseList)
                            }
                        }
                        if (tvCamera.isVisible) {
                            this.changeFab(true, requireContext(), pager)
                        }
                    }
                    .setNeutralButton(getString(R.string.select_all)) { _, which ->
                        val chooseList = mutableListOf<String>()
                        cameras.forEach { _ ->
                            checkedIndex.add(which)
                        }
                        when (viewPager.currentItem) {
                            0 -> {
                                adapter.chooseCamera(chooseList)
                            }
                            1 -> {
                                adapter.chooseCamera(chooseList)
                            }
                        }
                        if (tvCamera.isVisible) {
                            this.changeFab(true, requireContext(), pager)
                        }
                    }
                    .setNegativeButton(R.string.cancel_button) { _, _ ->
                        if (tvCamera.isVisible) {
                            this.changeFab(true, requireContext(), pager)
                        }
                    }
                    .create()
                    .show()
            }

            fabRover.setOnClickListener {
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
                        if (tvCamera.isVisible) {
                            this.changeFab(true, requireContext(), pager)
                        }
                    }
                    .setNeutralButton(getString(R.string.select_all)) { _, which ->
                        val chooseList = mutableListOf<String>()
                        rovers.forEach { _ ->
                            checkedIndex.add(which)
                        }
                        exchangeViewModel.selectChoosedRovers(chooseList)
                        if (tvCamera.isVisible) {
                            this.changeFab(true, requireContext(), pager)
                        }
                    }
                    .setNegativeButton(R.string.cancel_button) { _, _ ->
                        if (tvCamera.isVisible) {
                            this.changeFab(true, requireContext(), pager)
                        }
                    }
                    .create()
                    .show()
            }
        }
    }

//    private fun chooseDate() {
//        val c = Calendar.getInstance()
//        val year = c.get(Calendar.YEAR)
//        val month = c.get(Calendar.MONTH)
//        val day = c.get(Calendar.DAY_OF_MONTH)
//        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
//        val maxDate = format.parse(photoDate)
//        val minDate = format.parse(minDate)
//
//        val dpd = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
//            val newDate = "$year-${month + 1}-$dayOfMonth"
//            setupExchangeInformation(roverName, newDate)
//            binding.changeFab(true, requireContext(), binding.pager)
//        }, year, month, day)
//        dpd.datePicker.maxDate = maxDate.time
//        dpd.datePicker.minDate = minDate.time
//        dpd.show()
//    }

    private val addToFavouriteOnClickListener = object : AddFavouriteOnClickListener {
        override fun addToFavouriteOnClicked(photo: Photo) {
//            detailViewModel.addPhotoToFavourite(photo)
//            Snackbar.make(
//                binding.scrollLayout,
//                getString(R.string.snack_add_favourite),
//                Snackbar.LENGTH_SHORT
//            ).show()
        }
    }

    private val deleteFavouriteOnClickListener = object : DeleteFavouriteOnClickListener {
        override fun deleteFavouriteOnClicked(photo: Photo) {
//            favouritesViewModel.deletePhoto(photo)
//            Snackbar.make(
//                binding.scrollLayoutFav,
//                getString(R.string.snack_delete),
//                Snackbar.LENGTH_SHORT
//            ).show()
        }
    }

    private val itemClickListener = object : ItemClickListener {
        override fun onItemClicked(photo: Photo) {
            //доделать
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModel.onDestroy()
    }
}