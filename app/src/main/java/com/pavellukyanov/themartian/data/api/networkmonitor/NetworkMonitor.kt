package com.pavellukyanov.themartian.data.api.networkmonitor

interface NetworkMonitor {
    fun isNetworkAvailable(): Boolean
}