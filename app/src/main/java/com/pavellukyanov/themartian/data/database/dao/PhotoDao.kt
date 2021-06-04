package com.pavellukyanov.themartian.data.database.dao

import androidx.room.*
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface PhotoDao {
    @Query("SELECT * FROM favourites WHERE id = :id")
    fun getPhoto(id: Long): Observable<PhotoEntity>

    @Query("SELECT * FROM favourites")
    fun getAllPhotos(): Observable<List<PhotoEntity>>

    @Query("SELECT * FROM favourites WHERE rover = :roverName AND dataEarth = :earthDate")
    fun getPhotoWithRoverNameAndDate(
        roverName: String,
        earthDate: String
    ): Observable<List<PhotoEntity>>

//    @Query("SELECT * FROM favourites WHERE isFavourite = :isFavourites")
//    fun getFavouritesPhoto(isFavourites: Int): Single<List<PhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photoEntity: PhotoEntity)

    @Update
    fun updatePhoto(photoEntity: PhotoEntity)

    @Query("DELETE FROM favourites WHERE id = :id")
    fun deletePhoto(id: Long)
}