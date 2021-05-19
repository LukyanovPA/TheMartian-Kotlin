package com.pavellukyanov.themartian.ui.main.favourites

import com.pavellukyanov.themartian.data.domain.Photo

interface DeleteFavouriteOnClickListener {
    fun deleteFavouriteOnClicked(photo: Photo)
}