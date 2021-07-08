package com.pavellukyanov.themartian.ui.main.roverdetails

import com.pavellukyanov.themartian.domain.photo.Photo

interface AddFavouriteOnClickListener {
    fun addToFavouriteOnClicked(photo: Photo)
}