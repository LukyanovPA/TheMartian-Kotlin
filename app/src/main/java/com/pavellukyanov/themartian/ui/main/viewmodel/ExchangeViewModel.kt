package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.pavellukyanov.themartian.data.api.models.Photo
import com.pavellukyanov.themartian.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor() : ViewModel() {
    private var _listPhoto: MutableLiveData<List<Photo>> = MutableLiveData()
    private val listPhoto: LiveData<List<Photo>> get() = _listPhoto

    fun selectListPhoto(list: List<Photo>) {
        _listPhoto.postValue(list)
    }

    fun returnListPhoto(): LiveData<List<Photo>> = listPhoto
}