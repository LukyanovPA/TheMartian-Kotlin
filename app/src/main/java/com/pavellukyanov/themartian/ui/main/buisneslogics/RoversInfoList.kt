package com.pavellukyanov.themartian.ui.main.buisneslogics

import android.util.Log
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.api.GoRetrofit
import com.pavellukyanov.themartian.data.model.RoverInfo
import com.pavellukyanov.themartian.ui.main.view.fragments.MainFragment
import kotlinx.coroutines.*

class RoversInfoList() {

    var list: MutableList<RoverInfo> = mutableListOf()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    private var scope = CoroutineScope(
        SupervisorJob() +
                Dispatchers.Default +
                exceptionHandler
    )

    fun getRoversInfoList(): MutableList<RoverInfo> {
        scope.launch { goNewCoroutine() }
        return list
    }

    private suspend fun goNewCoroutine() {
        val rovCur = scope.async { loadRovInf() }.await()
        rovCur.picture = R.drawable.curiosity

        val rovOpp = scope.async { loadOp() }.await()
        rovOpp.picture = R.drawable.opportunity

        val rovSpi = scope.async { loadSpi() }.await()
        rovSpi.picture = R.drawable.spirit

        list = mutableListOf()
        list.add(rovCur)
        list.add(rovOpp)
        list.add(rovSpi)

        //Log
        Log.d(LOG_TAG, "Лог в goNewCoroutine() - ${list.size}")
    }

    private suspend fun loadSpi(): RoverInfo = withContext(Dispatchers.IO) {
        GoRetrofit.apiManifestService.getRoverManifest(SPIRIT).photoManifest
    }

    private suspend fun loadOp(): RoverInfo = withContext(Dispatchers.IO) {
        GoRetrofit.apiManifestService.getRoverManifest(OPPORTUNITY).photoManifest
    }

    private suspend fun loadRovInf(): RoverInfo = withContext(Dispatchers.IO) {
        GoRetrofit.apiManifestService.getRoverManifest(CURIOSITY).photoManifest
    }

    companion object {
        private const val LOG_TAG = "roversInfo"
        private const val CURIOSITY = "Curiosity"
        private const val OPPORTUNITY = "Opportunity"
        private const val SPIRIT = "Spirit"
    }
}