package com.pavellukyanov.themartian.data.database

import android.util.Log
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pavellukyanov.themartian.data.di.App
import com.pavellukyanov.themartian.data.models.dbmodel.RoverInfoEntity
import com.pavellukyanov.themartian.data.repository.FinallyDataRepoImpl
import kotlinx.coroutines.*

@Database(entities = [RoverInfoEntity::class], version = 7)
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