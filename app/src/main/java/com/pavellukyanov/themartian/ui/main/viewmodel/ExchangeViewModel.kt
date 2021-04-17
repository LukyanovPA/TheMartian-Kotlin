package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.*
import com.pavellukyanov.themartian.data.api.models.Photo
import com.pavellukyanov.themartian.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor() : ViewModel() {
    private var _listPhoto: MutableLiveData<List<Photo>> = MutableLiveData()
    private val listPhoto: LiveData<List<Photo>> get() = _listPhoto
    private var _photo: MutableLiveData<Photo> = MutableLiveData()
    private val photo: LiveData<Photo> get() = _photo
    private var _photoId: MutableLiveData<Long> = MutableLiveData()
    private val photoId: LiveData<Long> get() = _photoId

    fun selectListPhoto(list: List<Photo>) {
        _listPhoto.postValue(list)
    }

    fun returnListPhoto(): LiveData<List<Photo>> = listPhoto

    fun addPhotoToFavourite(photo: Photo) {
        _photo.postValue(photo)
    }

    fun getPhotoToFavourite(): LiveData<Photo> = photo

    fun deletePhotoInFavourite(id: Long) {
        _photoId.postValue(id)
    }

    fun getIdPhoto(): LiveData<Long> = photoId
}