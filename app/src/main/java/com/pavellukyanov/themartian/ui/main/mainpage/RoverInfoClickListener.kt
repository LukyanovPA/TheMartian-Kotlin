package com.pavellukyanov.themartian.ui.main.mainpage

import com.pavellukyanov.themartian.domain.rover_info.RoverInfo

interface RoverInfoClickListener {
    fun onRoverInfoItemClicked(roverInfo: RoverInfo)
}