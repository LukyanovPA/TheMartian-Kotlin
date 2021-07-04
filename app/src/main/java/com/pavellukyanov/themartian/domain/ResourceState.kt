package com.pavellukyanov.themartian.domain

sealed class ResourceState<out T>{
    data class Success<out T>(val data: T) : ResourceState<T>()
    data class Error(val error: Throwable) : ResourceState<Nothing>()
    object Loading : ResourceState<Nothing>()
}