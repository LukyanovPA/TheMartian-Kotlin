package com.pavellukyanov.themartian.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pavellukyanov.themartian.domain.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

//@HiltViewModel
open class BaseViewModel<T : Any> : ViewModel() {
    internal val dispose: CompositeDisposable = CompositeDisposable()
    private var _response = MutableLiveData<ResourceState<T>>()
    private val response: LiveData<ResourceState<T>> get() = _response

    open fun onSetResource(resource: Single<T>) {
        dispose.add(resource
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _response.postValue(ResourceState.Loading) }
            .subscribe(
                { listRoverInfo ->
                    _response.postValue(ResourceState.Success(listRoverInfo))
                },
                { error ->
                    _response.postValue(ResourceState.Error(error))
                }
            )
        )
    }

    open fun onSubscribeViewModel(): LiveData<ResourceState<T>> = response

    open fun onDestroy() {
        dispose.dispose()
    }
}