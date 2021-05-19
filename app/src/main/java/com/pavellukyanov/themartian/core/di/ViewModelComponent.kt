package com.pavellukyanov.themartian.core.di

import com.pavellukyanov.themartian.core.di.module.ViewModelModule
import com.pavellukyanov.themartian.ui.main.viewmodel.MainViewModel
import dagger.Component

@Component( modules = arrayOf(
    ViewModelModule::class
))
interface ViewModelComponent {

    fun inject(mainViewModel: MainViewModel)
}