package com.pavellukyanov.themartian.data.database.repo

import androidx.lifecycle.LiveData
import com.pavellukyanov.themartian.data.database.MartianDatabase
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import javax.inject.Inject

class PhotoDatabaseImpl @Inject constructor(
    private val database: MartianDatabase
): PhotoDatabase {
    override suspend fun getPhoto(id: Long): PhotoEntity =
        database.photoDao().getPhoto(id)

    override suspend fun getAllPhoto(): LiveData<List<PhotoEntity>> {
        return database.photoDao().getAllPhotos()
    }

    override suspend fun insertPhoto(photoEntity: PhotoEntity) {
        database.photoDao().insertPhoto(photoEntity)
    }

    override suspend fun updatePhoto(photoEntity: PhotoEntity) {
        database.photoDao().updatePhoto(photoEntity)
    }

    override suspend fun deletePhoto(id: Long) {
        database.photoDao().deletePhoto(id)
    }
}