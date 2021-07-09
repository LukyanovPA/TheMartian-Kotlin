package com.pavellukyanov.themartian.ui.main.roverdetails

import com.pavellukyanov.themartian.domain.favourites.AddPhotoToFavouriteInteractor
import com.pavellukyanov.themartian.domain.favourites.DeletePhotoInFavouriteInteractor
import com.pavellukyanov.themartian.domain.photo.LoadPhotoForEarthDateInteractor
import com.pavellukyanov.themartian.domain.photo.Photo
import com.pavellukyanov.themartian.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import javax.inject.Inject

@HiltViewModel
class RoverDetailsViewModel @Inject constructor(
    private val photoInteractor: LoadPhotoForEarthDateInteractor,
    private val addFavouriteInteractor: AddPhotoToFavouriteInteractor,
    private val deletePhotoInFavouriteInteractor: DeletePhotoInFavouriteInteractor
) : BaseViewModel<List<Photo>>() {

    private val dispose = CompositeDisposable()

    fun doChangePhotoDate(
        roverName: String,
        earthData: Single<String>
    ) {
        dispose.add(
            earthData
                .subscribe { date ->
                    onSetResource(photoInteractor.invoke(roverName, date))
                }
        )
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

    override fun onDestroy() {
        super.onDestroy()
        dispose.dispose()
    }
}