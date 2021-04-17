package com.pavellukyanov.themartian.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pavellukyanov.themartian.data.database.models.PhotoEntity

@Dao
interface PhotoDao {
    @Query("SELECT * FROM favourites WHERE id = :id")
    suspend fun getPhoto(id: Long): PhotoEntity

    @Query("SELECT * FROM favourites")
    fun getAllPhotos(): LiveData<List<PhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photoEntity: PhotoEntity): Long

    @Update
    suspend fun updatePhoto(photoEntity: PhotoEntity)

    @Query("DELETE FROM favourites WHERE id = :id")
    suspend fun deletePhoto(id: Long)
}