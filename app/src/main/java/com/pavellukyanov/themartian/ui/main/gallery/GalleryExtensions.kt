package com.pavellukyanov.themartian.ui.main.gallery

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.viewpager2.widget.ViewPager2
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.databinding.FragmentPagerBinding
import java.text.SimpleDateFormat
import java.util.*

fun FragmentPagerBinding.changeFab(
    isOpen: Boolean,
    context: Context,
    viewPager: ViewPager2
) {
    val fabClose: Animation = AnimationUtils.loadAnimation(context, R.anim.fab_close)
    val fabOpen: Animation = AnimationUtils.loadAnimation(context, R.anim.fab_open)
    val fabRotateAnticlock: Animation =
        AnimationUtils.loadAnimation(context, R.anim.fab_rotate_anticlock)
    val fabRotateClock: Animation =
        AnimationUtils.loadAnimation(context, R.anim.fab_rotate_clock)

    if (isOpen) {
        when (viewPager.currentItem) {
            1 -> {
                tvRover.visibility = View.INVISIBLE
                fabRover.startAnimation(fabClose)
                fabRover.isClickable = false
            }
            0 -> {
                tvDate.visibility = View.INVISIBLE
                fabDate.startAnimation(fabClose)
                fabDate.isClickable = false
            }
        }
        tvCamera.visibility = View.INVISIBLE
        fabCamera.startAnimation(fabClose)
        fabSetting.startAnimation(fabRotateAnticlock)
        fabCamera.isClickable = false
    } else {
        when (viewPager.currentItem) {
            1 -> {
                fabRover.startAnimation(fabOpen)
                fabRover.isClickable = true
                tvRover.visibility = View.VISIBLE
            }
            0 -> {
                fabDate.startAnimation(fabOpen)
                fabDate.isClickable = true
                tvDate.visibility = View.VISIBLE
            }
        }
        fabCamera.startAnimation(fabOpen)
        fabSetting.startAnimation(fabRotateClock)
        fabCamera.isClickable = true
        tvCamera.visibility = View.VISIBLE
    }
}

fun FragmentPagerBinding.chooseDate(
    photoDate: String,
    minDate: String,
    context: Context
): String {
    var newDate = ""
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val maxDate = format.parse(photoDate)
    val minDate = format.parse(minDate)

    val dpd = DatePickerDialog(context, { _, year, month, dayOfMonth ->
        val _newDate = "$year-${month + 1}-$dayOfMonth"
        newDate = _newDate
        changeFab(true, context, this.pager)
    }, year, month, day)
    dpd.datePicker.maxDate = maxDate.time
    dpd.datePicker.minDate = minDate.time
    dpd.show()
    return newDate
}