package com.pavellukyanov.themartian.ui.main.viewmodel

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.pavellukyanov.themartian.data.repository.ManifestRepository
import com.pavellukyanov.themartian.utils.Resource
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ManifestViewModel(private val manifestRepository: ManifestRepository) : ViewModel() {

    fun getRoverManifest(roverName: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = manifestRepository.getRoverManifest(roverName)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}