package com.pavellukyanov.themartian.ui.main.gallery

import com.pavellukyanov.themartian.domain.ResourceState
import com.pavellukyanov.themartian.domain.favourites.AddPhotoToFavouriteInteractor
import com.pavellukyanov.themartian.domain.favourites.DeletePhotoInFavouriteInteractor
import com.pavellukyanov.themartian.domain.favourites.GetAllFavouritesPhotoInteractor
import com.pavellukyanov.themartian.domain.photo.LoadPhotoForEarthDateInteractor
import com.pavellukyanov.themartian.domain.photo.Photo
import com.pavellukyanov.themartian.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val photoInteractor: LoadPhotoForEarthDateInteractor,
    private val favouriteInteractor: GetAllFavouritesPhotoInteractor,
    private val addFavouriteInteractor: AddPhotoToFavouriteInteractor,
    private val deletePhotoInFavouriteInteractor: DeletePhotoInFavouriteInteractor
) : BaseViewModel<List<Photo>>() {
    private var networkCameras = arrayListOf<String>()
    private var favouritesCameras = arrayListOf<String>()
    private val dispose = CompositeDisposable()

    fun doChangePhotoDate(
        roverName: String,
        earthData: String
    ) {
        onSetResource(photoInteractor.invoke(roverName, earthData))
        dispose.add(
            onSubscribeViewModel()
                .subscribe(this::setupNetworkCameras)
        )
    }

    fun getFavouritePhotos() {
        onSetResource(favouriteInteractor.invoke())
        dispose.add(
            onSubscribeViewModel()
                .subscribe(this::setupFavouritesCameras)
        )
    }

    private fun setupNetworkCameras(listPhoto: ResourceState<List<Photo>>) {
        if (listPhoto is ResourceState.Success) {
            val cameras: HashSet<String> = hashSetOf()
            listPhoto.data.forEach { photo ->
                cameras.add(photo.camera)
                networkCameras.clear()
                cameras.forEach { camera ->
                    networkCameras.add(camera)
                }
            }
        }
    }

    private fun setupFavouritesCameras(listPhoto: ResourceState<List<Photo>>) {
        if (listPhoto is ResourceState.Success) {
            val cameras: HashSet<String> = hashSetOf()
            listPhoto.data.forEach { photo ->
                cameras.add(photo.camera)
                favouritesCameras.clear()
                cameras.forEach { camera ->
                    favouritesCameras.add(camera)
                }
            }
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

    fun availableNetworkCameras(): ArrayList<String> = networkCameras

    fun availableFavouriteCameras(): ArrayList<String> = favouritesCameras

    override fun onDestroy() {
        super.onDestroy()
        dispose.dispose()
    }
}