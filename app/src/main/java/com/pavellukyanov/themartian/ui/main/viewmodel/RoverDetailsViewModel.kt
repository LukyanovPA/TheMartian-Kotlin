package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.*
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.data.domain.Photo
import com.pavellukyanov.themartian.data.repository.RoverInfoRepo
import com.pavellukyanov.themartian.data.repository.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RoverDetailsViewModel @Inject constructor(private val roverInfoRepo: RoverInfoRepo) : ViewModel() {
    private var _roverInfo: MutableLiveData<RoverInfoEntity> = MutableLiveData()
    private val roverInfo: LiveData<RoverInfoEntity> get() = _roverInfo
    private var _marsData: MutableLiveData<List<Photo>> = MutableLiveData()
    private val marsData: LiveData<List<Photo>> get() = _marsData

    fun getPhotosForSol(roverName: String, sol: Long) = liveData(Dispatchers.IO) {
        emit(ResourceState.loading(data = null))
        try {
            emit(ResourceState.success(data = roverInfoRepo.getPhotoForSol(roverName, sol)))
        } catch (exception: Exception) {
            emit(ResourceState.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getPhotosForEarthData(roverName: String, earthData: String): LiveData<List<Photo>> {
        viewModelScope.launch {
            _marsData.postValue(roverInfoRepo.getPhotoForEarthDate(roverName, earthData))
        }
        return marsData
    }

    fun getRoverInfo(roverName: String): LiveData<RoverInfoEntity> {
        viewModelScope.launch {
            _roverInfo.postValue(roverInfoRepo.loadAllRoverInfo(roverName))
        }
        return roverInfo
    }

    fun addPhotoToFavourite(photo: Photo) {
        viewModelScope.launch {
            roverInfoRepo.insertPhotoToFavourite(photo)
        }
    }
}