package com.pavellukyanov.themartian.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import com.pavellukyanov.themartian.data.api.ApiHelper
import com.pavellukyanov.themartian.data.api.ApiManifestHelper
import com.pavellukyanov.themartian.data.database.DataBase
import com.pavellukyanov.themartian.data.repository.DataBaseRepoImpl
import com.pavellukyanov.themartian.data.repository.MainRepoImpl
import com.pavellukyanov.themartian.data.repository.NetworkRepoImpl
import com.pavellukyanov.themartian.data.repository.old.ManifestRepository
import com.pavellukyanov.themartian.ui.main.viewmodel.MainViewModel
import java.lang.IllegalArgumentException

class MainViewModFactory(
    private val database: DataBase,
    private val apiManifestHelper: ApiManifestHelper,
    private val apiHelper: ApiHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                MainRepoImpl(
                    DataBaseRepoImpl(database),
                    NetworkRepoImpl(apiManifestHelper, apiHelper)
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}