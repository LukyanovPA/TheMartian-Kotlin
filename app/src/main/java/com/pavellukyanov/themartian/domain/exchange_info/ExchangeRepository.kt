package com.pavellukyanov.themartian.domain.exchange_info

import com.pavellukyanov.themartian.domain.rover_info.RoverInfo

interface ExchangeRepository {

    fun chooseRoverInMainScreen(roverInfo: RoverInfo)
}