package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.*
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.data.repository.MainRepo
import com.pavellukyanov.themartian.data.repository.network.NetworkRepo
import com.pavellukyanov.themartian.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepo: MainRepo) : ViewModel() {
    private var _roverInfo: MutableLiveData<List<RoverInfoEntity>> = MutableLiveData()
    private val roverInfo: LiveData<List<RoverInfoEntity>> get() = _roverInfo

    init {
        viewModelScope.launch {
            mainRepo.getRoverManifest().observeForever { _roverInfo.postValue(it) }
            mainRepo.setRoverInfoFromWorker()
        }
    }

    fun getRoverManifest(): LiveData<List<RoverInfoEntity>> = roverInfo
}