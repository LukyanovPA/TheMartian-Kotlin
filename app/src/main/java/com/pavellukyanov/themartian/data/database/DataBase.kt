package com.pavellukyanov.themartian.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pavellukyanov.themartian.data.models.database.RoverInfoEntity
import com.pavellukyanov.themartian.data.models.network.RoverInfo
import com.pavellukyanov.themartian.di.App

@Database(entities = [RoverInfoEntity::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun roverInfoDao(): RoverInfoDao

    companion object {
        private const val DATABASE_NAME = "Martian.db"

        val instance: DataBase by lazy {
            Room.databaseBuilder(
                App.getApp().applicationContext,
                DataBase::class.java,
                DATABASE_NAME
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
