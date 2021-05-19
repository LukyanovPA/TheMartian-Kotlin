package com.pavellukyanov.themartian.ui.main.roverdetails

import com.pavellukyanov.themartian.data.domain.Photo

interface AddFavouriteOnClickListener {
    fun addToFavouriteOnClicked(photo: Photo)
}