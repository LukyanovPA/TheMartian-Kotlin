package com.pavellukyanov.themartian.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pavellukyanov.themartian.data.database.dao.FavouritesDao
import com.pavellukyanov.themartian.data.database.dao.PhotoDao
import com.pavellukyanov.themartian.data.database.dao.RoverInfoDao
import com.pavellukyanov.themartian.data.database.models.FavouriteEntity
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity

@Database(entities = [RoverInfoEntity::class,
    PhotoEntity::class,
    FavouriteEntity::class],
    version = 5,
    exportSchema = false)
abstract class MartianDatabase : RoomDatabase() {
    abstract fun roverInfoDao(): RoverInfoDao
    abstract fun photoDao(): PhotoDao
    abstract fun favouritesDao(): FavouritesDao
}