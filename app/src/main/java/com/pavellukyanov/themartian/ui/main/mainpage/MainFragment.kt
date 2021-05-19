package com.pavellukyanov.themartian.ui.main.mainpage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.domain.RoverInfo
import com.pavellukyanov.themartian.data.repository.ResourceState
import com.pavellukyanov.themartian.databinding.FragmentMainBinding
import com.pavellukyanov.themartian.ui.main.decoration.*
import com.pavellukyanov.themartian.ui.main.mainpage.adapter.MainAdapter
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
        mainViewModel.getRoverManifest().observe(viewLifecycleOwner, { onStateReceive(it) })
    }

    private fun onStateReceive(resourceState: ResourceState<List<RoverInfo>>) {
        when (resourceState) {
            is ResourceState.Success -> handleSuccessState(resourceState.data)
            is ResourceState.Loading -> handleLoadingState(true)
            is ResourceState.Error -> handleErrorState(resourceState.error)
        }
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

    private fun handleSuccessState(roversInfo: List<RoverInfo>) {
        handleLoadingState(false)
        mainAdapter.apply {
            addRoversInfo(roversInfo)
        }
    }

    private fun handleLoadingState(state: Boolean) {

    }

    private fun handleErrorState(error: Throwable?) {

    }

    private val clickListener = object : RoverInfoClickListener {
        override fun onRoverInfoItemClicked(roverInfo: RoverInfo) {
            showRoverDetailsFragment(roverInfo)
        }
    }

    private fun showRoverDetailsFragment(roverInfo: RoverInfo) {
        val action = MainFragmentDirections.actionMainFragmentToFragmentPager(roverInfo)
        findNavController().navigate(action)
    }

    override fun onDetach() {
        super.onDetach()
        mainViewModel.onDestroy()
    }
}
