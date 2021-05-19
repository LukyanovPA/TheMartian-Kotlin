package com.pavellukyanov.themartian.core.di.module

import android.content.Context
import androidx.room.Room
import com.pavellukyanov.themartian.data.database.MartianDatabase
import com.pavellukyanov.themartian.data.database.dao.PhotoDao
import com.pavellukyanov.themartian.data.database.dao.RoverInfoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): MartianDatabase {
        return Room.databaseBuilder(
            context,
            MartianDatabase::class.java,
            "MartianDatabase.db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideRoverInfoDao(database: MartianDatabase): RoverInfoDao {
        return database.roverInfoDao()
    }

    @Provides
    fun providePhotoDao(database: MartianDatabase): PhotoDao {
        return database.photoDao()
    }
}