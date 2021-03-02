package com.pavellukyanov.themartian.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pavellukyanov.themartian.data.models.dbmodel.RoverInfoEntity
import com.pavellukyanov.themartian.data.models.networkmodel.RoverInfo

interface DataBaseRepoInterface {
    suspend fun setRoverInfo(roverInfoList: List<RoverInfo>)

    suspend fun deleteRoverInfo(roverName: String)

    suspend fun getAllRoverInfo(): LiveData<List<RoverInfoEntity>>

    fun clearAllBase()
}