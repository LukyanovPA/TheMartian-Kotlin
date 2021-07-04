package com.pavellukyanov.themartian.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.loadCircle(
    url: Any?
) {
    Glide
        .with(this)
        .asBitmap()
        .load(url)
        .circleCrop()
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(this)
}