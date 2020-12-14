package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.pavellukyanov.themartian.data.repository.MainRepository
import com.pavellukyanov.themartian.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainVewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getPhotos(roverName: String, sol: Long) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getPhoto(roverName, sol)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getPhotosEarthData(roverName: String, earthData: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getPhotoEarthData(roverName, earthData)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}