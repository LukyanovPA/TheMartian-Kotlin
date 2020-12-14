package com.pavellukyanov.themartian.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pavellukyanov.themartian.data.api.ApiHelper
import com.pavellukyanov.themartian.data.api.ApiManifestHelper
import com.pavellukyanov.themartian.data.repository.MainRepository
import com.pavellukyanov.themartian.data.repository.ManifestRepository
import com.pavellukyanov.themartian.ui.main.viewmodel.MainVewModel
import com.pavellukyanov.themartian.ui.main.viewmodel.ManifestViewModel
import java.lang.IllegalArgumentException

class ManifestViewModFactory(private val apiManifestHelper: ApiManifestHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ManifestViewModel::class.java)) {
            return ManifestViewModel(ManifestRepository(apiManifestHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}