package com.pavellukyanov.themartian.ui.main.mainpage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pavellukyanov.themartian.domain.ResourceState
import com.pavellukyanov.themartian.domain.RoverInfo
import com.pavellukyanov.themartian.domain.RoverInfoRepo
import com.pavellukyanov.themartian.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val roverInfoRepo: RoverInfoRepo) :
    BaseViewModel() {
    private var _state = MutableLiveData<ResourceState<List<RoverInfo>>>()
    private val state get() = _state

    fun getRoverManifest(): LiveData<ResourceState<List<RoverInfo>>> {
        _state.postValue(ResourceState.Loading)
        dispose.add(roverInfoRepo.loadAllRoverInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _state.postValue(ResourceState.Loading) }
            .subscribe(
                { listRoverInfo ->
                    Log.d("ttt", "viewModel - ${listRoverInfo.size}")
                    _state.postValue(ResourceState.Success(listRoverInfo))
                },
                { error ->
                    _state.postValue(ResourceState.Error(error))
                }
            )
        )
        return state
    }
}