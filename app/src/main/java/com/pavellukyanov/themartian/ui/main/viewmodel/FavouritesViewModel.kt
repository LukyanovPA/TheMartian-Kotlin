package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.domain.DomainPhoto
import com.pavellukyanov.themartian.data.repository.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val mainRepo: MainRepo) : ViewModel() {
    private var _favourites: MutableLiveData<List<DomainPhoto>> = MutableLiveData()
    private val favourites: LiveData<List<DomainPhoto>> get() = _favourites
    private var isFavourite = false

    init {
        viewModelScope.launch {
            mainRepo.getAllFavouritePhoto().observeForever {
                _favourites.postValue(it)
            }
        }
    }

    fun getAllFavourites(): LiveData<List<DomainPhoto>> = favourites

    fun deletePhoto(id: Long) {
        viewModelScope.launch {
            mainRepo.deletePhotoInFavourite(id)
        }
    }

    fun checkIsFavourite(id: Long): Boolean {
        viewModelScope.launch {
            isFavourite = mainRepo.checkFavourite(id)
        }
        return isFavourite
    }
}