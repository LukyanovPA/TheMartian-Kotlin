package com.pavellukyanov.themartian.ui.base

import com.pavellukyanov.themartian.domain.photo.Photo

interface ItemClickListener {
    fun onItemClicked(photo: Photo)
}