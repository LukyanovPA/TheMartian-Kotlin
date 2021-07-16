package com.pavellukyanov.themartian.ui.base

import com.pavellukyanov.themartian.domain.photo.Photo

interface DeleteFavouriteOnClickListener {
    fun deleteFavouriteOnClicked(photo: Photo)
}