package com.pavellukyanov.themartian.ui.main.adapters

import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity

interface ItemClickListener {
    fun onItemClicked(roverInfoEntity: RoverInfoEntity)
}