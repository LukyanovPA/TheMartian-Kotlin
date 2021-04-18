package com.pavellukyanov.themartian.ui.main.adapters

import com.pavellukyanov.themartian.data.api.models.Photo
import com.pavellukyanov.themartian.data.domain.DomainPhoto

interface AddFavouriteOnClickListener {
    fun addToFavouriteOnClicked(photo: DomainPhoto)
}