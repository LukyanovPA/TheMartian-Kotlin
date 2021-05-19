package com.pavellukyanov.themartian.ui.main.adapters

import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.data.domain.RoverInfo

interface RoverInfoClickListener {
    fun onRoverInfoItemClicked(roverInfo: RoverInfo)
}