package com.pavellukyanov.themartian.ui.main.view.glide

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pavellukyanov.themartian.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_main.*

class GetGlide(){

    fun setBacground(context: Context?, imageView: ImageView) {
        if (context != null) {
            Glide.with(context)
                .asBitmap()
                .load(R.drawable.mars)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .into(imageView)
        }
    }

    fun setRoverPictureInMain(context: Context?, imageView: ImageView) {
        if (context != null) {
            Glide.with(context)
                .asDrawable()
                .load(R.drawable.curiosity)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .into(imageView)
        }
    }
}