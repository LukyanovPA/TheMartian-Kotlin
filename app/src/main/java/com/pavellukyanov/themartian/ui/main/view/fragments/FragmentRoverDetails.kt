package com.pavellukyanov.themartian.ui.main.view.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.api.ApiHelper
import com.pavellukyanov.themartian.data.api.GoRetrofit
import com.pavellukyanov.themartian.data.model.Photo
import com.pavellukyanov.themartian.data.model.RoverInfo
import com.pavellukyanov.themartian.databinding.FragmentRoverDetailsBinding
import com.pavellukyanov.themartian.ui.base.ViewModelFactory
import com.pavellukyanov.themartian.ui.main.adapter.GalleryAdapter
import com.pavellukyanov.themartian.ui.main.viewmodel.MainVewModel
import com.pavellukyanov.themartian.utils.Status
import java.util.*

class FragmentRoverDetails : Fragment(R.layout.fragment_rover_details) {
    private lateinit var vmRover: MainVewModel
    private lateinit var adapter: GalleryAdapter
    private lateinit var roverInfo: RoverInfo
    private lateinit var binding: FragmentRoverDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roverInfo = requireNotNull(requireArguments()).getParcelable(ROVER_KEY)!!
        binding = FragmentRoverDetailsBinding.bind(view)

        initViewModel()
        setupUI(roverInfo)
        getData(roverInfo)
    }

//    private fun maxDateToLong(): Long {
//        val date: String? = roverInfo.maxDate?.filter { it.isDigit() }
//        val maxDate: Long = date?.toLongOrNull(10)!!
//        return maxDate
//    }

    private fun initViewModel() {
        val factory = ViewModelFactory(ApiHelper(GoRetrofit.apiService))
        vmRover = ViewModelProvider(this, factory).get(MainVewModel::class.java)
    }

    private fun setDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
//        val maxDate = maxDateToLong()

        val dpd =
            this.context?.let {
                DatePickerDialog(
                    it,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, monthOfYear)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                        binding.detailsTotalPhoto.text = DateUtils.formatDateTime(
                            context,
                            calendar.timeInMillis,
                            DateUtils.FORMAT_SHOW_DATE
                        )
                    },
                    year,
                    month,
                    day
                )
            }
// Разобраться с максимальной и минимальной датой!
//        val optop: Date = Date(20100312)
//        val mOooo: String = "03122010"
//        dpd?.datePicker?.setMaxDate(10)
        dpd?.show()
    }

    private fun setupUI(roverInfo: RoverInfo) {
        binding.rvDetails.layoutManager = GridLayoutManager(context, 2)
        adapter = GalleryAdapter(arrayListOf())
        binding.rvDetails.adapter = adapter

        binding.buttonBack.setOnClickListener { activity?.onBackPressed() }
        binding.setDateBT?.setOnClickListener { setDate() }

        binding.roverDetailsName.text = roverInfo.name
        binding.detailsLaunchDate.text = roverInfo.launchData
        binding.detailsTotalPhoto.text = roverInfo.totalPhotos.toString()
        binding.detailsLatestPhoto.text = roverInfo.maxDate.toString()

        when (roverInfo.name) {
            CURIOSITY -> binding.detailRoverPicture.setImageDrawable(context?.let {
                getDrawable(
                    it,
                    R.drawable.curiosity_rover
                )
            })
            OPPORTUNITY -> binding.detailRoverPicture.setImageDrawable(context?.let {
                getDrawable(
                    it,
                    R.drawable.opportunity_rover
                )
            })
            SPIRIT -> binding.detailRoverPicture.setImageDrawable(context?.let {
                getDrawable(
                    it,
                    R.drawable.spirit_rover
                )
            })
        }
    }

    private fun getData(roverInfo: RoverInfo) {
        roverInfo.maxDate?.let {
            roverInfo.name?.let { it1 ->
                vmRover.getPhotosEarthData(it1, it.toString()).observe(this, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                binding.rvDetails.visibility = View.VISIBLE
                                binding.progressBar3.visibility = View.GONE
                                //для теста
                                val ms = resource.data
                                Log.d(LOG_TAG, ms.toString())
                                resource.data?.let { mars -> retrieveList(mars.photos) }
                            }
                            Status.ERROR -> {
                                binding.rvDetails.visibility = View.VISIBLE
                                binding.progressBar3.visibility = View.GONE
                                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                                Log.d(LOG_TAG, it.message.toString())
                            }
                            Status.LOADING -> {
                                binding.progressBar3.visibility = View.VISIBLE
                                binding.rvDetails.visibility = View.GONE
                            }
                        }
                    }
                })
            }
        }
    }

    private fun retrieveList(photos: List<Photo>) {
        Log.d("stater", photos.toString())
        adapter.apply {
            addPhotos(photos)
            notifyDataSetChanged()
        }
    }

    companion object {
        private const val ROVER_KEY = "rover"
        private const val LOG_TAG = "roversInfo"
        private const val CURIOSITY = "Curiosity"
        private const val OPPORTUNITY = "Opportunity"
        private const val SPIRIT = "Spirit"

        fun newInstance(roverInfo: RoverInfo): Fragment = FragmentRoverDetails().apply {
            arguments = bundleOf(ROVER_KEY to roverInfo)
        }
    }
}