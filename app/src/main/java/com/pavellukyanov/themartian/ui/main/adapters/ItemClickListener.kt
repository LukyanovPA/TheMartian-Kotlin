package com.pavellukyanov.themartian.ui.main.adapters

import com.pavellukyanov.themartian.data.domain.DomainPhoto

interface ItemClickListener {
    fun onItemClicked(domainPhoto: DomainPhoto)
}