package com.pavellukyanov.themartian.data.mapper

interface Mapper<T, K> {
    operator fun invoke(source: T): K
}