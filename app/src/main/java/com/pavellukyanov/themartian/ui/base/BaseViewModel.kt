package com.pavellukyanov.themartian.ui.base

import androidx.lifecycle.ViewModel
import com.pavellukyanov.themartian.domain.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@HiltViewModel
open class BaseViewModel<T : Any> : ViewModel() {
    private val dispose: CompositeDisposable = CompositeDisposable()
    private lateinit var response: Single<ResourceState<T>>

    open fun onSetResource(resource: Single<T>) {
        response = Single.just(ResourceState.Loading)
        dispose.add(resource
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response = Single.just(ResourceState.Loading) }
            .subscribe(
                { listRoverInfo ->
                    response = Single.just(ResourceState.Success(listRoverInfo))
                },
                { error ->
                    response = Single.just((ResourceState.Error(error)))
                }
            )
        )
    }

    open fun onSubscribeViewModel(): Single<ResourceState<T>> = response

    open fun onDestroy() {
        dispose.dispose()
    }
}