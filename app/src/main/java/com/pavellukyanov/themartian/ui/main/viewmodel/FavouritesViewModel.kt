package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavellukyanov.themartian.data.domain.Photo
import com.pavellukyanov.themartian.data.repository.PhotoRepo
import com.pavellukyanov.themartian.data.repository.ResourceState
import com.pavellukyanov.themartian.data.repository.RoverInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val photoRepo: PhotoRepo) : ViewModel() {
    private var _favourites: MutableLiveData<ResourceState<List<Photo>>> = MutableLiveData()
    private val favourites: LiveData<ResourceState<List<Photo>>> get() = _favourites
    private var _isFavourite: MutableLiveData<ResourceState<Boolean>> = MutableLiveData()
    private val isFavourite get() = _isFavourite
    private val compositeDisposable by lazy { CompositeDisposable() }

    init {
        _favourites.postValue(ResourceState.Loading)
        compositeDisposable.add(photoRepo.getAllFavouritePhoto()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _favourites.postValue(ResourceState.Loading) }
            .subscribe(
                { success ->
                    _favourites.postValue(ResourceState.Success(success))
                },
                { error ->
                    _favourites.postValue(ResourceState.Error(error))
                }
            )
        )
    }

    fun getAllFavourites(): LiveData<ResourceState<List<Photo>>> = favourites

    fun deletePhoto(photo: Photo) = photoRepo.deletePhotoInFavourite(photo)

    fun checkIsFavourite(id: Long): LiveData<ResourceState<Boolean>> {
        _isFavourite.postValue(ResourceState.Loading)
        compositeDisposable.add(photoRepo.checkFavourite(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _isFavourite.postValue(ResourceState.Loading) }
            .subscribe(
                { success ->
                    _isFavourite.postValue(ResourceState.Success(success))
                },
                { error ->
                    _isFavourite.postValue(ResourceState.Error(error))
                }
            )
        )
        return isFavourite
    }

    fun onDestroy() = compositeDisposable.dispose()
}