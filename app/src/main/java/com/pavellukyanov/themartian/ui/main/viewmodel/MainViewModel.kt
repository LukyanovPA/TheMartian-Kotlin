package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.pavellukyanov.themartian.data.repository.NetworkRepoInterface
import com.pavellukyanov.themartian.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainViewModel(private val networkRepoInterface: NetworkRepoInterface) : ViewModel() {

    fun getRoverManifest() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = networkRepoInterface.getRoverManifest()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}