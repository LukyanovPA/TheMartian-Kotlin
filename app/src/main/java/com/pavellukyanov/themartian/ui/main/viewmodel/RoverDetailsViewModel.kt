package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.pavellukyanov.themartian.data.repository.network.NetworkRepo
import com.pavellukyanov.themartian.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class RoverDetailsViewModel(private val networkRepo: NetworkRepo) : ViewModel() {

    fun getPhotosForSol(roverName: String, sol: Long) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = networkRepo.getPhotoForSol(roverName, sol)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getPhotosForEarthData(roverName: String, earthData: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = networkRepo.getPhotoForEarthDate(roverName, earthData)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}