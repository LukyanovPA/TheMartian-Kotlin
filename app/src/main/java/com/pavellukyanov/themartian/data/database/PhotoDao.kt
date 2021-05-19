package com.pavellukyanov.themartian.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface PhotoDao {
    @Query("SELECT * FROM favourites WHERE id = :id")
    fun getPhoto(id: Long): Single<PhotoEntity>

    @Query("SELECT * FROM favourites")
    fun getAllPhotos(): Single<List<PhotoEntity>>

    @Query("SELECT * FROM favourites WHERE rover = :roverName AND dataEarth = :earthDate")
    fun getPhotoWithRoverNameAndDate(
        roverName: String,
        earthDate: String
    ): Single<List<PhotoEntity>>

    @Query("SELECT * FROM favourites WHERE isFavourite = :isFavourites")
    fun getFavouritesPhoto(isFavourites: Int): Single<List<PhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photoEntity: PhotoEntity): Completable

    @Update
    fun updatePhoto(photoEntity: PhotoEntity): Completable

    @Query("DELETE FROM favourites WHERE id = :id")
    fun deletePhoto(id: Long): Completable
}