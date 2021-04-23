package com.pavellukyanov.themartian.ui.main.adapters

import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity

interface RoverInfoClickListener {
    fun onRoverInfoItemClicked(roverInfoEntity: RoverInfoEntity)
}