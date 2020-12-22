package com.pavellukyanov.themartian.ui.main.helpers

import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.model.RoverInfo

class GetRoverInfos {

    fun getCuriosity(): RoverInfo = RoverInfo(
        R.drawable.curiosity_rover,
        R.drawable.curiosity,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

    fun getOpportunity(): RoverInfo = RoverInfo(
        R.drawable.opportunity_rover,
        R.drawable.opportunity,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

    fun getSpirit(): RoverInfo = RoverInfo(
        R.drawable.spirit_rover,
        R.drawable.spirit,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}