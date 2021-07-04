package com.pavellukyanov.themartian.ui.main.mainpage

import com.pavellukyanov.themartian.domain.RoverInfo

interface RoverInfoClickListener {
    fun onRoverInfoItemClicked(roverInfo: RoverInfo)
}