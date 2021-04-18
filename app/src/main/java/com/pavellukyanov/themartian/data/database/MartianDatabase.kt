package com.pavellukyanov.themartian.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity

@Database(entities = [RoverInfoEntity::class, PhotoEntity::class], version = 3, exportSchema = true)
abstract class MartianDatabase : RoomDatabase() {
    abstract fun roverInfoDao(): RoverInfoDao
    abstract fun photoDao(): PhotoDao
}