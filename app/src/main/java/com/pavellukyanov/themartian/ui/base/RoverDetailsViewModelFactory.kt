package com.pavellukyanov.themartian.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pavellukyanov.themartian.data.api.ApiNASA
import com.pavellukyanov.themartian.data.repository.network.NetworkRepoImpl
import com.pavellukyanov.themartian.ui.main.viewmodel.RoverDetailsViewModel
import java.lang.IllegalArgumentException

class RoverDetailsViewModelFactory(
    private val apiService: ApiNASA
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoverDetailsViewModel::class.java)) {
            return RoverDetailsViewModel(
                NetworkRepoImpl(apiService)
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}