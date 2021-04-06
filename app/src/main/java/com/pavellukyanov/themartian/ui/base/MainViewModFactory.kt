package com.pavellukyanov.themartian.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pavellukyanov.themartian.data.api.ApiNASA
import com.pavellukyanov.themartian.data.api.Router
import com.pavellukyanov.themartian.data.repository.network.NetworkRepo
import com.pavellukyanov.themartian.data.repository.network.NetworkRepoImpl
import com.pavellukyanov.themartian.ui.main.viewmodel.MainViewModel
import java.lang.IllegalArgumentException

class MainViewModFactory/*(
    private val apiService: ApiNASA
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                NetworkRepoImpl(
                    apiService
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}*/