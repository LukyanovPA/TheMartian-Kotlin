package com.pavellukyanov.themartian.ui.main.view.fragments

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.model.Photo
import com.pavellukyanov.themartian.data.model.RoverInfo
import com.pavellukyanov.themartian.ui.main.adapter.MainAdapter
import com.pavellukyanov.themartian.ui.main.buisneslogics.RoversInfoList
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    private var scope = CoroutineScope(
        SupervisorJob() +
                Dispatchers.Main +
                exceptionHandler
    )

    private lateinit var resultList: MutableList<RoverInfo>
    private lateinit var adapter: MainAdapter
    private val flag: Boolean = true

    override fun onStart() {
        super.onStart()
        setupUI()
        scope.launch { setupData() }
    }

    private fun setupData() {
        mainRecycler.visibility = View.VISIBLE
        resultList = mutableListOf()
        val getList = RoversInfoList()
        getList.getRoversInfoList()
        resultList = getList.list

        //Log
        Log.d(LOG_TAG, "Лог в setupData() - ${resultList.size}")
        Log.d(LOG_TAG, "Лог в setupData() - ${getList.list.toString()}")
    }

    private fun setupUI() {
        mainRecycler.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        adapter = MainAdapter(arrayListOf())
        mainRecycler.adapter = adapter
    }

    private fun retrieveList(roversInfo: MutableList<RoverInfo>) {

        //Log
        Log.d(LOG_TAG, "Лог в retrieveList() - ${roversInfo.size}")

        adapter.apply {
            addRoversInfo(roversInfo)
            notifyDataSetChanged()
        }
    }

    companion object {
        private const val LOG_TAG = "roversInfo"
        private const val CURIOSITY = "Curiosity"
        private const val OPPORTUNITY = "Opportunity"
        private const val SPIRIT = "Spirit"
    }
}