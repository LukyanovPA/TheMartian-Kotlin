package com.pavellukyanov.themartian.ui.main.view.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.model.RoverInfo
import com.pavellukyanov.themartian.ui.main.viewmodel.ManifestViewModel

class FragmentRoverDetails: Fragment(R.layout.fragment_rover_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val roverInfo: RoverInfo? = requireNotNull(requireArguments()).getParcelable(ROVER_KEY)
    }

    companion object {
        private const val ROVER_KEY = "rover"

        fun newInstance(roverInfo: RoverInfo): Fragment = FragmentRoverDetails().apply {
            arguments = bundleOf(ROVER_KEY to roverInfo)
        }
    }
}