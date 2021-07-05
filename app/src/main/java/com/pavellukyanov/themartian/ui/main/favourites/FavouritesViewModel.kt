package com.pavellukyanov.themartian.ui.main.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pavellukyanov.themartian.domain.Photo
import com.pavellukyanov.themartian.domain.PhotoRepo
import com.pavellukyanov.themartian.domain.ResourceState
import com.pavellukyanov.themartian.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val photoRepo: PhotoRepo) : BaseViewModel() {
    private var _favourites: MutableLiveData<ResourceState<List<Photo>>> = MutableLiveData()
    private val favourites: LiveData<ResourceState<List<Photo>>> get() = _favourites
    private var _isFavourite: MutableLiveData<ResourceState<Boolean>> = MutableLiveData()
    private val isFavourite get() = _isFavourite

    fun getAllFavourites(): LiveData<ResourceState<List<Photo>>> {
        _favourites.postValue(ResourceState.Loading)
        dispose.add(photoRepo.getAllFavouritePhoto()
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
        return favourites
    }

    fun deletePhoto(photo: Photo) = photoRepo.deletePhotoInFavourite(photo)

    fun checkIsFavourite(id: Long): LiveData<ResourceState<Boolean>> {
        _isFavourite.postValue(ResourceState.Loading)
        dispose.add(photoRepo.checkFavourite(id)
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
}