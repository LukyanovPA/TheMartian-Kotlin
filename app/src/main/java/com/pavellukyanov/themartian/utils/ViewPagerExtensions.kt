package com.pavellukyanov.themartian.utils

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.ui.main.gallery.adapter.GalleryAdapter
import com.pavellukyanov.themartian.ui.main.mainpage.adapter.MainAdapter

fun ViewPager2.bindRoverInfo(
    itemListSize: Int,
    tabLayout: TabLayout,
    context: Context,
    pagerAdapter: MainAdapter
) {
    adapter = pagerAdapter
    setPageTransformer(DepthPageTransformer())
    offscreenPageLimit = itemListSize
    tabLayout.onTableSelected(onUnselected = { tab ->
        (tab?.customView?.findViewById(R.id.tab_layout_item) as ImageView)
            .setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_swipe_indicator_fill
                )
            )
    }, onSelected = { tab ->
        (tab?.customView?.findViewById(R.id.tab_layout_item) as ImageView)
            .setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_swipe_indicator
                )
            )
    })
    overScrollMode = ViewPager2.OVER_SCROLL_NEVER
}

fun TabLayout.onTableSelected(
    onReselected: ((TabLayout.Tab?) -> Unit)? = null,
    onUnselected: ((TabLayout.Tab?) -> Unit)? = null,
    onSelected: ((TabLayout.Tab?) -> Unit)? = null
) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
            onReselected?.invoke(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            onUnselected?.invoke(tab)
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            onSelected?.invoke(tab)
        }
    })
}

fun ViewPager2.bindGalleryPager(
    itemListSize: Int,
    pagerAdapter: GalleryAdapter
) {
    adapter = pagerAdapter
    setPageTransformer(DepthPageTransformer())
    offscreenPageLimit = itemListSize
    overScrollMode = ViewPager2.OVER_SCROLL_NEVER
}