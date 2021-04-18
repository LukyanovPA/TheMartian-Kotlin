package com.pavellukyanov.themartian.ui.main.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPageAdapter(
    context: Context,
    listFragment: ArrayList<Fragment>,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    private val list = listFragment

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list[position]
}