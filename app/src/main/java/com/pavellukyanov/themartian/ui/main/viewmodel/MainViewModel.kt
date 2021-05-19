package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.*
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.data.domain.RoverInfo
import com.pavellukyanov.themartian.data.repository.ResourceState
import com.pavellukyanov.themartian.data.repository.RoverInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val roverInfoRepo: RoverInfoRepo) : ViewModel() {
    private var _roverInfo: MutableLiveData<ResourceState<List<RoverInfo>>> = MutableLiveData()
    private val roverInfo: LiveData<ResourceState<List<RoverInfo>>> get() = _roverInfo
    private val compositeDisposable by lazy { CompositeDisposable() }

    init {
        _roverInfo.postValue(ResourceState.Loading)
        compositeDisposable.add(roverInfoRepo.loadAllRoverInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _roverInfo.postValue(ResourceState.Loading) }
            .subscribe(
                { listRoverInfo ->
                    _roverInfo.postValue(ResourceState.Success(listRoverInfo))
                },
                { error ->
                    _roverInfo.postValue(ResourceState.Error(error))
                }
            )
        )
    }

    fun getRoverManifest(): LiveData<ResourceState<List<RoverInfo>>> = roverInfo

    fun onDestroy() {
        compositeDisposable.dispose()
    }
}