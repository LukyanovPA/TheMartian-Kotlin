package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.databinding.FragmentMainBinding
import com.pavellukyanov.themartian.ui.main.adapter.ItemClickListener
import com.pavellukyanov.themartian.ui.main.adapter.LinePagerIndicatorDecoration
import com.pavellukyanov.themartian.ui.main.adapter.MainAdapter
import com.pavellukyanov.themartian.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val mainAdapter by lazy { MainAdapter(mutableListOf(), clickListener) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        subscribeLiveData()
        setupUI()
    }

    private fun subscribeLiveData() {
        mainViewModel.getRoverManifest().observe(viewLifecycleOwner, { response ->
            response?.let { data ->
                retrieveList(data)
            }
        })
    }

    private fun setupUI() {
        binding.mainRecycler.apply {
            adapter = mainAdapter
            layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            addItemDecoration(LinePagerIndicatorDecoration())
        }
    }

    private fun retrieveList(roversInfo: List<RoverInfoEntity>) {
        mainAdapter.apply {
            addRoversInfo(roversInfo)
            notifyDataSetChanged()
        }
    }

    val clickListener = object : ItemClickListener {
        override fun onItemClicked(roverName: String, maxDate: String) {
            showRoverDetailsFragment(roverName, maxDate)
        }
    }

    private fun showRoverDetailsFragment(roverName: String, maxDate: String) {
        val action = MainFragmentDirections.actionMainFragmentToFragmentPager(roverName, maxDate)
        findNavController().navigate(action)
    }
}
