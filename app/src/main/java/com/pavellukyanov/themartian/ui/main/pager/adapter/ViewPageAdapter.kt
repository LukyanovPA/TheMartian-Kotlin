package com.pavellukyanov.themartian.ui.main.pager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPageAdapter(
    listFragment: List<Fragment>,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    private val list = listFragment

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list[position]
}