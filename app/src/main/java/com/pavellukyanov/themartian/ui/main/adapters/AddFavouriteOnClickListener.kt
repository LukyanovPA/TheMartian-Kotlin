package com.pavellukyanov.themartian.ui.main.adapters

import com.pavellukyanov.themartian.data.domain.Photo

interface AddFavouriteOnClickListener {
    fun addToFavouriteOnClicked(photo: Photo)
}