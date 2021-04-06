package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.api.ApiNASA
import com.pavellukyanov.themartian.data.api.Router
import com.pavellukyanov.themartian.data.api.models.RoverInfo
import com.pavellukyanov.themartian.data.repository.network.NetworkRepoImpl
import com.pavellukyanov.themartian.ui.base.MainViewModFactory
import com.pavellukyanov.themartian.ui.main.adapter.ItemClickListener
import com.pavellukyanov.themartian.ui.main.adapter.LinePagerIndicatorDecoration
import com.pavellukyanov.themartian.ui.main.adapter.MainAdapter
import com.pavellukyanov.themartian.ui.main.viewmodel.MainViewModel
import com.pavellukyanov.themartian.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeLiveData()
        setupUI()
    }

    private fun subscribeLiveData() {
        mainViewModel.getRoverManifest().observe(this.viewLifecycleOwner, { response ->
            response?.let { data ->
                when (response.status) {
                    Status.SUCCESS -> {
                        mainRecycler.visibility = View.VISIBLE
                        progressBar2.visibility = View.GONE
                        response.data?.let { retrieveList(it.toMutableList()) }
                    }
                    Status.LOADING -> {
                        progressBar2.visibility = View.VISIBLE
                        mainRecycler.visibility = View.GONE
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
                        Log.d(LOG_TAG, data.message.toString())
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
        private const val LOG_TAG = "MainFragment"
    }
}
