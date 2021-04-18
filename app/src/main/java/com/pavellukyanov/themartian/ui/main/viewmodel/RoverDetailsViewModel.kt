package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.*
import com.pavellukyanov.themartian.data.api.models.Mars
import com.pavellukyanov.themartian.data.api.models.Photo
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.data.domain.DomainPhoto
import com.pavellukyanov.themartian.data.repository.MainRepo
import com.pavellukyanov.themartian.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RoverDetailsViewModel @Inject constructor(private val mainRepo: MainRepo) : ViewModel() {
    private var _roverInfo: MutableLiveData<RoverInfoEntity> = MutableLiveData()
    private val roverInfo: LiveData<RoverInfoEntity> get() = _roverInfo
    private var _marsData: MutableLiveData<List<DomainPhoto>> = MutableLiveData()
    private val marsData: LiveData<List<DomainPhoto>> get() = _marsData

    fun getPhotosForSol(roverName: String, sol: Long) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepo.getPhotoForSol(roverName, sol)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getPhotosForEarthData(roverName: String, earthData: String): LiveData<List<DomainPhoto>> {
        viewModelScope.launch {
            _marsData.postValue(mainRepo.getPhotoForEarthDate(roverName, earthData))
        }
        return marsData
    }

    fun getRoverInfo(roverName: String): LiveData<RoverInfoEntity> {
        viewModelScope.launch {
            _roverInfo.postValue(mainRepo.getRoverInfo(roverName))
        }
        return roverInfo
    }

    fun addPhotoToFavourite(photo: DomainPhoto) {
        viewModelScope.launch {
            mainRepo.insertPhotoToFavourite(photo)
        }
    }
}