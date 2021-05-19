package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.*
import javax.inject.Inject

class ExchangeViewModel @Inject constructor() : ViewModel() {
    private var _camerasNetworkData: MutableLiveData<HashSet<String>> = MutableLiveData()
    private val camerasNetworkData: LiveData<HashSet<String>> get() = _camerasNetworkData
    private var _camerasFavouritesData: MutableLiveData<HashSet<String>> = MutableLiveData()
    private val camerasFavouritesData: LiveData<HashSet<String>> get() = _camerasFavouritesData
    private var _chooseCam: MutableLiveData<List<String>> = MutableLiveData()
    private val chooseCam: LiveData<List<String>> get() = _chooseCam
    private var _chooseFavCam: MutableLiveData<List<String>> = MutableLiveData()
    private val chooseFavCam: LiveData<List<String>> get() = _chooseFavCam
    private var _actualDate: MutableLiveData<Pair<String, String>> = MutableLiveData()
    private val actualDate: LiveData<Pair<String, String>> get() = _actualDate
    private var _rovers: MutableLiveData<HashSet<String>> = MutableLiveData()
    private val rovers: LiveData<HashSet<String>> get() = _rovers
    private var _chooseRovers: MutableLiveData<List<String>> = MutableLiveData()
    private val chooseRovers: LiveData<List<String>> get() = _chooseRovers

    fun selectActualDate(roverName: String, date: String) {
        val roverData = Pair(roverName, date)
        _actualDate.postValue(roverData)
    }

    fun returnActualDate(): LiveData<Pair<String, String>> = actualDate

    fun selectNetworkCameras(list: HashSet<String>) {
        _camerasNetworkData.postValue(list)
    }

    fun returnNetworkCameras(): LiveData<HashSet<String>> = camerasNetworkData

    fun selectFavouritesCameras(list: HashSet<String>) {
        _camerasFavouritesData.postValue(list)
    }

    fun returnFavouritesCameras(): LiveData<HashSet<String>> = camerasFavouritesData

    fun selectedChooseCam(list: List<String>) {
        _chooseCam.postValue(list)
    }

    fun returnChooseCam(): LiveData<List<String>> = chooseCam

    fun selectedChooseFavCam(list: List<String>) {
        _chooseFavCam.postValue(list)
    }

    fun returnChooseFavCam(): LiveData<List<String>> = chooseFavCam

    fun selectRovers(list: HashSet<String>) {
        _rovers.postValue(list)
    }

    fun returnRovers(): LiveData<HashSet<String>> = rovers

    fun selectChoosedRovers(list: List<String>) {
        _chooseRovers.postValue(list)
    }

    fun returnChoosedRovers(): LiveData<List<String>> = chooseRovers

    fun clearViewModel() = onCleared()

    override fun onCleared() {
        super.onCleared()
    }
}