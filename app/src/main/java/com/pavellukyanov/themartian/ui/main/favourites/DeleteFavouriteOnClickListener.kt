package com.pavellukyanov.themartian.ui.main.favourites

import com.pavellukyanov.themartian.domain.photo.Photo

interface DeleteFavouriteOnClickListener {
    fun deleteFavouriteOnClicked(photo: Photo)
}