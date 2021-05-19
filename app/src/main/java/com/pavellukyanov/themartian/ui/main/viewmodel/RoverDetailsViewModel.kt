package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.*
import com.pavellukyanov.themartian.data.domain.Photo
import com.pavellukyanov.themartian.data.repository.PhotoRepo
import com.pavellukyanov.themartian.data.repository.ResourceState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RoverDetailsViewModel @Inject constructor(private val photoRepo: PhotoRepo) : ViewModel() {
    private var _marsData: MutableLiveData<ResourceState<List<Photo>>> = MutableLiveData()
    private val marsData: LiveData<ResourceState<List<Photo>>> get() = _marsData
    private val compositeDisposable by lazy { CompositeDisposable() }

    fun getPhotosForEarthData(
        roverName: String,
        earthData: String
    ): LiveData<ResourceState<List<Photo>>> {
        _marsData.postValue(ResourceState.Loading)
        compositeDisposable.add(photoRepo.loadPhotoForEarthDate(roverName, earthData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _marsData.postValue(ResourceState.Loading) }
            .subscribe(
                { success ->
                    _marsData.postValue(ResourceState.Success(success))
                },
                { error ->
                    _marsData.postValue(ResourceState.Error(error))
                }
            )
        )
        return marsData
    }

    fun addPhotoToFavourite(photo: Photo) {
        photoRepo.addPhotoToFavourite(photo)
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }
}