package com.pavellukyanov.themartian.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pavellukyanov.themartian.data.api.ApiManifestHelper
import com.pavellukyanov.themartian.data.database.DataBase
import com.pavellukyanov.themartian.data.repository.DataBaseRepoImpl
import com.pavellukyanov.themartian.data.repository.FinallyDataRepoImpl
import com.pavellukyanov.themartian.data.repository.NetworkManifestRepoImpl
import com.pavellukyanov.themartian.data.repository.old.ManifestRepository
import com.pavellukyanov.themartian.ui.main.viewmodel.TestViewModel
import java.lang.IllegalArgumentException

class TestViewModelFactory(
    private val dataBase: DataBase,
    private val apiManifestHelper: ApiManifestHelper/*private val dataBaseRepoImpl: DataBaseRepoImpl*/
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
            return TestViewModel(
                FinallyDataRepoImpl(
                    DataBaseRepoImpl(dataBase),
                    NetworkManifestRepoImpl(apiManifestHelper)
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}