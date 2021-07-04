package com.pavellukyanov.themartian.ui.main.roverdetails

import com.pavellukyanov.themartian.domain.Photo

interface ItemClickListener {
    fun onItemClicked(photo: Photo)
}