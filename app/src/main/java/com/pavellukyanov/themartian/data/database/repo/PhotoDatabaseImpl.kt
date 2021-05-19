package com.pavellukyanov.themartian.data.database.repo

import com.pavellukyanov.themartian.data.database.MartianDatabase
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.domain.Photo
import com.pavellukyanov.themartian.data.mapper.PhotoDomainToEntity
import com.pavellukyanov.themartian.data.mapper.PhotoEntityToDomain
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PhotoDatabaseImpl @Inject constructor(
    private val database: MartianDatabase
) : PhotoDatabase {
    override fun getPhoto(id: Long): Single<Photo> =
        database.photoDao().getPhoto(id)
            .subscribeOn(Schedulers.io())
            .map { PhotoEntityToDomain().invoke(it) }

    override fun getFavouritesPhoto(): Single<List<Photo>> =
        database.photoDao().getFavouritesPhoto(1)
            .subscribeOn(Schedulers.io())
            .map { mappingPhotoEntityToDomain(it) }

    override fun getPhotoWithRoverNameAndDate(
        roverName: String,
        earthDate: String
    ): Single<List<Photo>> =
        database.photoDao().getPhotoWithRoverNameAndDate(roverName, earthDate)
            .subscribeOn(Schedulers.io())
            .map { mappingPhotoEntityToDomain(it) }

    private fun mappingPhotoEntityToDomain(listPhotoEntity: List<PhotoEntity>): List<Photo> {
        val listPhoto = mutableListOf<Photo>()
        listPhotoEntity.forEach {
            listPhoto.add(PhotoEntityToDomain().invoke(it))
        }
        return listPhoto
    }

    override fun insertPhoto(photo: Photo): Completable =
        Completable.fromAction {
            database.photoDao().insertPhoto(
                PhotoDomainToEntity().invoke(photo)
            )
        }

    override fun updatePhoto(photo: Photo): Completable =
        Completable.fromAction {
            database.photoDao().updatePhoto(
                PhotoDomainToEntity().invoke(photo)
            )
        }

    override fun deletePhoto(id: Long): Completable =
        Completable.fromAction { database.photoDao().deletePhoto(id) }

    override fun addToFavourite(photo: Photo): Completable =
        Completable.fromAction {
            updatePhoto(photo.apply { isFavourite = 1 })
        }

    override fun deleteInFavourite(photo: Photo): Completable =
        Completable.fromAction {
            updatePhoto(photo.apply { isFavourite = 0 })
        }

    override fun chekFavourite(id: Long): Single<Boolean> =
        database.photoDao().getPhoto(id)
            .subscribeOn(Schedulers.io())
            .map {
                when (it.isFavourite) {
                    1 -> true
                    else -> false
                }
            }
}