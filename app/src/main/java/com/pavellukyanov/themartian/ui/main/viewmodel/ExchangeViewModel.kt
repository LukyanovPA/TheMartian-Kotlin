package com.pavellukyanov.themartian.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.pavellukyanov.themartian.data.api.models.Photo
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.domain.DomainPhoto
import com.pavellukyanov.themartian.data.repository.MainRepoImpl
import com.pavellukyanov.themartian.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor() : ViewModel() {
    private var _cameras: MutableLiveData<HashSet<String>> = MutableLiveData()
    private val cameras: LiveData<HashSet<String>> get() = _cameras
    private var _chooseCam: MutableLiveData<List<String>> = MutableLiveData()
    private val chooseCam: LiveData<List<String>> get() = _chooseCam
    private var _actualDate: MutableLiveData<Pair<String, String>> = MutableLiveData()
    private val actualDate: LiveData<Pair<String, String>> get() = _actualDate

    fun selectActualDate(roverName: String, date: String) {
        val roverData = Pair(roverName, date)
        _actualDate.postValue(roverData)
    }

    fun returnActualDate(): LiveData<Pair<String, String>> = actualDate

    fun selectCameras(list: HashSet<String>) {
        _cameras.postValue(list)
    }

    fun returnCameras(): LiveData<HashSet<String>> = cameras

    fun selectedChooseCam(list: List<String>) {
        _chooseCam.postValue(list)
    }

    fun returnChooseCam(): LiveData<List<String>> = chooseCam
}