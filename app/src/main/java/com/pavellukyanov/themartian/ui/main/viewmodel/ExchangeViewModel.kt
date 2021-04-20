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
    private var _listPhoto: MutableLiveData<List<DomainPhoto>> = MutableLiveData()
    private val listPhoto: LiveData<List<DomainPhoto>> get() = _listPhoto
    private var _photo: MutableLiveData<DomainPhoto> = MutableLiveData()
    private val photo: LiveData<DomainPhoto> get() = _photo
    private var _photoId: MutableLiveData<Long> = MutableLiveData()
    private val photoId: LiveData<Long> get() = _photoId
    private var _favourites: MutableLiveData<List<PhotoEntity>> = MutableLiveData()
    private val favourites: LiveData<List<PhotoEntity>> get() = _favourites
    private var _actualDate: MutableLiveData<Pair<String, String>> = MutableLiveData()
    private val actualDate: LiveData<Pair<String, String>> get() = _actualDate

//    fun selectListPhoto(list: List<DomainPhoto>) {
//        _listPhoto.postValue(list)
//    }
//
//    fun returnListPhoto(): LiveData<List<DomainPhoto>> = listPhoto

    fun selectActualDate(roverName: String, date: String) {
        val roverData = Pair(roverName, date)
        _actualDate.postValue(roverData)
    }

    fun returnActualDate(): LiveData<Pair<String, String>> = actualDate

    fun addPhotoToFavourite(photo: DomainPhoto) {
        _photo.postValue(photo)
    }

    fun getPhotoToFavourite(): LiveData<DomainPhoto> = photo

    fun selectAllFavourites(list: List<PhotoEntity>) {
        _favourites.postValue(list)
    }

    fun getAllFavourites(): LiveData<List<PhotoEntity>> = favourites

    fun deletePhotoInFavourite(id: Long) {
        _photoId.postValue(id)
    }

    fun getIdPhoto(): LiveData<Long> = photoId
}