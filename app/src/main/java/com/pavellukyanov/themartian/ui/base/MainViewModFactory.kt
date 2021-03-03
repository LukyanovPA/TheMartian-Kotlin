package com.pavellukyanov.themartian.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pavellukyanov.themartian.data.api.ApiHelper
import com.pavellukyanov.themartian.data.api.ApiManifestHelper
import com.pavellukyanov.themartian.data.api.ApiNASA
import com.pavellukyanov.themartian.data.repository.NetworkRepoImpl
import com.pavellukyanov.themartian.ui.main.viewmodel.MainViewModel
import java.lang.IllegalArgumentException

class MainViewModFactory(
    private val apiHelper: ApiHelper,
    private val apiManifestHelper: ApiManifestHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                NetworkRepoImpl(
                    apiHelper,
                    apiManifestHelper
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}