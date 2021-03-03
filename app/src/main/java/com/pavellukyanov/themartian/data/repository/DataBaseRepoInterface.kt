package com.pavellukyanov.themartian.data.repository

import androidx.lifecycle.LiveData
import com.pavellukyanov.themartian.data.models.database.RoverInfoEntity
import com.pavellukyanov.themartian.data.models.network.RoverInfo

interface DataBaseRepoInterface {
    suspend fun setRoverInfo(roverInfoList: List<RoverInfo>)

    suspend fun getAllRoverInfo(): LiveData<List<RoverInfoEntity>>
}