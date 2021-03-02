package com.pavellukyanov.themartian.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.pavellukyanov.themartian.data.models.dbmodel.RoverInfoEntity
import com.pavellukyanov.themartian.data.models.networkmodel.RoverInfo

interface FinallyDataRepoInterface {
    suspend fun getRoversInfo(): LiveData<List<RoverInfoEntity>>
}