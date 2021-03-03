package com.pavellukyanov.themartian.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pavellukyanov.themartian.data.api.ApiHelper
import com.pavellukyanov.themartian.data.api.ApiManifestHelper
import com.pavellukyanov.themartian.data.repository.NetworkRepoImpl
import com.pavellukyanov.themartian.ui.main.viewmodel.RoverDetailsViewModel
import java.lang.IllegalArgumentException

class RoverDetailsViewModelFactory(
    private val apiHelper: ApiHelper,
    private val apiManifestHelper: ApiManifestHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoverDetailsViewModel::class.java)) {
            return RoverDetailsViewModel(
                NetworkRepoImpl(
                    apiHelper,
                    apiManifestHelper
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}