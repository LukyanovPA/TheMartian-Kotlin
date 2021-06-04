package com.pavellukyanov.themartian.data.database.dao

import androidx.room.*
import com.pavellukyanov.themartian.data.database.models.FavouriteEntity
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FavouritesDao {

    @Query("SELECT * FROM favourites_photo WHERE id = :id")
    fun getFavouritePhoto(id: Long): Observable<FavouriteEntity>

    @Query("SELECT * FROM favourites_photo")
    fun getAllFavouritesPhotos(): Observable<List<FavouriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photoEntity: FavouriteEntity)

    @Update
    fun updatePhoto(photoEntity: FavouriteEntity)

    @Query("DELETE FROM favourites_photo WHERE id = :id")
    fun deletePhoto(id: Long)
}