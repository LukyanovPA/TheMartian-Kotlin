package com.pavellukyanov.themartian.ui.main.adapters

import com.pavellukyanov.themartian.data.api.models.Photo

interface AddFavouriteOnClickListener {
    fun addToFavouriteOnClicked(photo: Photo)
}