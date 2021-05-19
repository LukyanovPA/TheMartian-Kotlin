package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.*
import com.pavellukyanov.themartian.data.domain.RoverInfo
import com.pavellukyanov.themartian.data.repository.ResourceState
import com.pavellukyanov.themartian.data.repository.RoverInfoRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

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