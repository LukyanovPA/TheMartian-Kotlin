package com.pavellukyanov.themartian.ui.main.gallery

import com.pavellukyanov.themartian.domain.favourites.AddPhotoToFavouriteInteractor
import com.pavellukyanov.themartian.domain.favourites.CheckFavouritesInteractor
import com.pavellukyanov.themartian.domain.favourites.DeletePhotoInFavouriteInteractor
import com.pavellukyanov.themartian.domain.photo.LoadPhotoForEarthDateInteractor
import com.pavellukyanov.themartian.domain.photo.Photo
import com.pavellukyanov.themartian.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableCompletableObserver
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val photoInteractor: LoadPhotoForEarthDateInteractor,
    private val addFavouriteInteractor: AddPhotoToFavouriteInteractor,
    private val deletePhotoInFavouriteInteractor: DeletePhotoInFavouriteInteractor,
    private val checkFavouritesInteractor: CheckFavouritesInteractor
) : BaseViewModel<List<Photo>>() {
    private var networkCameras = arrayListOf<String>()

    fun doChangePhotoDate(
        roverName: String,
        earthData: String
    ) {
        onSetResource(photoInteractor.invoke(roverName, earthData))
    }

    fun setupNetworkCameras(listPhoto: List<Photo>) {
        val cameras: HashSet<String> = hashSetOf()
        listPhoto.forEach { photo ->
            cameras.add(photo.camera)
        }
        networkCameras.clear()
        cameras.forEach { camera ->
            networkCameras.add(camera)
        }
    }

    fun addPhotoToFavourite(photo: Photo): Boolean {
        var status = false
        dispose.add(
            addFavouriteInteractor.invoke(photo)
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        status = true
                    }

                    override fun onError(e: Throwable) {
                        status = false
                    }
                })
        )
        return status
    }

    fun deletePhotoToFavourite(photo: Photo): Boolean {
        var status = false
        dispose.add(
            deletePhotoInFavouriteInteractor.invoke(photo)
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        status = true
                    }

                    override fun onError(e: Throwable) {
                        status = false
                    }
                })
        )
        return status
    }

    fun availableNetworkCameras(): Array<CharSequence> = networkCameras.toTypedArray()
}