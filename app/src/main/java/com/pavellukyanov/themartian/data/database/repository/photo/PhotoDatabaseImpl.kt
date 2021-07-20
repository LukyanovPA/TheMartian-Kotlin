package com.pavellukyanov.themartian.data.database.repository.photo

import com.pavellukyanov.themartian.data.database.MartianDatabase
import com.pavellukyanov.themartian.data.database.models.FavouriteEntity
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.mapper.photo.FavouritesToDomain
import com.pavellukyanov.themartian.data.mapper.photo.PhotoDomainToEntity
import com.pavellukyanov.themartian.data.mapper.photo.PhotoDomainToFavourites
import com.pavellukyanov.themartian.data.mapper.photo.PhotoEntityToDomain
import com.pavellukyanov.themartian.domain.photo.Photo
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class PhotoDatabaseImpl @Inject constructor(
    private val database: MartianDatabase
) : PhotoDatabase {
    override fun getPhoto(id: Long): Observable<Photo> =
        database.photoDao().getPhoto(id)
            .map { PhotoEntityToDomain().invoke(it) }

    override fun getFavouritesPhoto(): Observable<List<Photo>> =
        database.favouritesDao().getAllFavouritesPhotos()
            .map { mappingFavouriteToDomain(it) }

    override fun getPhotoWithRoverNameAndDate(
        roverName: String,
        earthDate: String
    ): Observable<List<Photo>> =
        database.photoDao().getPhotoWithRoverNameAndDate(roverName, earthDate)
            .map { mappingPhotoEntityToDomain(it) }

    private fun mappingPhotoEntityToDomain(listPhotoEntity: List<PhotoEntity>): List<Photo> {
        val listPhoto = mutableListOf<Photo>()
        listPhotoEntity.forEach {
            listPhoto.add(PhotoEntityToDomain().invoke(it))
        }
        return listPhoto
    }

    private fun mappingFavouriteToDomain(listFavouriteEntity: List<FavouriteEntity>): List<Photo> {
        val listPhoto = mutableListOf<Photo>()
        listFavouriteEntity.forEach {
            listPhoto.add(FavouritesToDomain().invoke(it))
        }
        return listPhoto
    }

    override fun insertPhoto(photo: Photo) {
        database.photoDao().insertPhoto(
            PhotoDomainToEntity().invoke(photo)
        )
    }

    override fun deletePhoto(id: Long) {
        database.photoDao().deletePhoto(id)
    }

    override fun addToFavourite(photo: Photo): Completable {
        return Completable.fromAction {
            database.favouritesDao().insertPhoto(
                PhotoDomainToFavourites().invoke(photo)
            )
        }
    }

    override fun deleteInFavourite(photo: Photo): Completable {
        return Completable.fromAction {
            database.favouritesDao().deletePhoto(photo.id)
        }
    }

    override fun chekFavourite(id: Long): Observable<Boolean> =
        database.favouritesDao().getFavouritePhoto(id)
            .map {
                isFavourites(id, it)
            }

    private fun isFavourites(id: Long, favouriteEntity: FavouriteEntity): Boolean =
        id == favouriteEntity.id
}