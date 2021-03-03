package com.pavellukyanov.themartian.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pavellukyanov.themartian.data.api.ApiHelper
import com.pavellukyanov.themartian.data.repository.old.MainRepository
import com.pavellukyanov.themartian.ui.main.viewmodel.RoverDetailsVewModel
import java.lang.IllegalArgumentException

class RoverDetailsViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoverDetailsVewModel::class.java)) {
            return RoverDetailsVewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}