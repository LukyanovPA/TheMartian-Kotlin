package com.pavellukyanov.themartian.ui.main.mainpage

import com.pavellukyanov.themartian.data.domain.RoverInfo

interface RoverInfoClickListener {
    fun onRoverInfoItemClicked(roverInfo: RoverInfo)
}