package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavellukyanov.themartian.data.domain.Photo
import com.pavellukyanov.themartian.data.repository.RoverInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val roverInfoRepo: RoverInfoRepo) : ViewModel() {
    private var _favourites: MutableLiveData<List<Photo>> = MutableLiveData()
    private val favourites: LiveData<List<Photo>> get() = _favourites
    private var isFavourite = false

    init {
        viewModelScope.launch {
            roverInfoRepo.getAllFavouritePhoto().observeForever {
                _favourites.postValue(it)
            }
        }
    }

    fun getAllFavourites(): LiveData<List<Photo>> = favourites

    fun deletePhoto(id: Long) {
        viewModelScope.launch {
            roverInfoRepo.deletePhotoInFavourite(id)
        }
    }

    fun checkIsFavourite(id: Long): Boolean {
        viewModelScope.launch {
            isFavourite = roverInfoRepo.checkFavourite(id)
        }
        return isFavourite
    }
}