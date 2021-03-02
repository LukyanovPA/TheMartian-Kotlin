package com.pavellukyanov.themartian.ui.main.viewmodel

import androidx.lifecycle.*
import com.pavellukyanov.themartian.data.models.dbmodel.RoverInfoEntity
import com.pavellukyanov.themartian.data.models.networkmodel.RoverInfo
import com.pavellukyanov.themartian.data.repository.DataBaseRepoInterface
import com.pavellukyanov.themartian.data.repository.FinallyDataRepoInterface
import com.pavellukyanov.themartian.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.reflect.Array.get

class TestViewModel(private val finallyDataRepoInterface: FinallyDataRepoInterface) : ViewModel() {
    private var getResult: MutableLiveData<List<RoverInfoEntity>> = MutableLiveData()
//    private var callToFrag: LiveData<List<RoverInfoEntity>> get() = getResult

    private fun observDataBase() {
        viewModelScope.launch(Dispatchers.Main) {
            finallyDataRepoInterface.getRoversInfo().observeForever( {addListRovInfo(it) } )
        }
    }

    private fun addListRovInfo(list: List<RoverInfoEntity>) {
        getResult.postValue(list)
    }

    fun getRoverManifest(): LiveData<List<RoverInfoEntity>> {
        observDataBase()
        val callToFrag = getResult
//        getResult.postValue(result)
        return callToFrag
    }
}