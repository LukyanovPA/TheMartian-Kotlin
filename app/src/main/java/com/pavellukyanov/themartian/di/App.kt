package com.pavellukyanov.themartian.di

import android.app.Application

class App : Application() {
    companion object {
        lateinit var instance: App
        fun getApp(): App = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}