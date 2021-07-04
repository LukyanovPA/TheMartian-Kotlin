package com.pavellukyanov.themartian.ui.main.mainpage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.databinding.FragmentMainBinding
import com.pavellukyanov.themartian.databinding.ItemTabRoverInfoBinding
import com.pavellukyanov.themartian.domain.RoverInfo
import com.pavellukyanov.themartian.ui.base.BaseFragment
import com.pavellukyanov.themartian.ui.main.mainpage.adapter.MainAdapter
import com.pavellukyanov.themartian.utils.bindRoverInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<List<RoverInfo>>(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()
    private val mainAdapter by lazy { MainAdapter(mutableListOf(), clickListener) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        mainViewModel.getRoverManifest()
            .observe(viewLifecycleOwner, (::onStateReceive))
    }

    override fun handleSuccessStateMovies(data: List<RoverInfo>) {
        super.handleSuccessStateMovies(data)
        with(binding) {
            pagerMain.bindRoverInfo(
                data.size,
                roverTabIndicators,
                requireContext(),
                mainAdapter
            )
            bindTabMediator(
                roverTabIndicators,
                pagerMain
            )
        }
        mainAdapter.addRoversInfo(data)
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

    private fun bindTabMediator(tabLayout: TabLayout, viewPager: ViewPager2) {
        TabLayoutMediator(tabLayout, viewPager) { tab, _ ->
            tab.view.isClickable = false
            tab.customView =
                ItemTabRoverInfoBinding.inflate(layoutInflater).tabLayoutItem
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        mainViewModel.onDestroy()
    }
}
