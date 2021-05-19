package com.pavellukyanov.themartian.data.database.repo

import androidx.lifecycle.LiveData
import com.pavellukyanov.themartian.data.database.models.PhotoEntity

interface PhotoDatabase {
    suspend fun getPhoto(id: Long): PhotoEntity

    suspend fun getAllPhoto(): LiveData<List<PhotoEntity>>

    suspend fun insertPhoto(photoEntity: PhotoEntity)

    suspend fun updatePhoto(photoEntity: PhotoEntity)

    suspend fun deletePhoto(id: Long)
}