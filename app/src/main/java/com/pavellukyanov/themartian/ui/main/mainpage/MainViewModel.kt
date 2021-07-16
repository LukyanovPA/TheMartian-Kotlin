package com.pavellukyanov.themartian.ui.main.mainpage

import com.pavellukyanov.themartian.domain.rover_info.LoadAllRoverInfoInteractor
import com.pavellukyanov.themartian.domain.rover_info.RoverInfo
import com.pavellukyanov.themartian.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadAllRoverInfoInteractor: LoadAllRoverInfoInteractor
) :
    BaseViewModel<List<RoverInfo>>() {

    init {
        onSetResource(loadAllRoverInfoInteractor.invoke())
    }
}