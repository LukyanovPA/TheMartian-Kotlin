package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.*
import com.pavellukyanov.themartian.data.repository.MainRepoInterface
import com.pavellukyanov.themartian.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(
    private val mainRepoInterface: MainRepoInterface
    ) : ViewModel() {

    fun getRoverManifest() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepoInterface.getRoversInfo()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}