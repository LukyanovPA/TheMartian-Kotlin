package com.pavellukyanov.themartian.ui.main.gallery

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.databinding.FragmentGalleryBinding
import com.pavellukyanov.themartian.ui.main.gallery.adapter.GalleryAdapter
import java.text.SimpleDateFormat
import java.util.*

fun FragmentGalleryBinding.changeFab(
    isOpen: Boolean,
    context: Context
) {
    val fabClose: Animation = AnimationUtils.loadAnimation(context, R.anim.fab_close)
    val fabOpen: Animation = AnimationUtils.loadAnimation(context, R.anim.fab_open)
    val fabRotateAnticlock: Animation =
        AnimationUtils.loadAnimation(context, R.anim.fab_rotate_anticlock)
    val fabRotateClock: Animation =
        AnimationUtils.loadAnimation(context, R.anim.fab_rotate_clock)

    if (isOpen) {
        tvDate.visibility = View.INVISIBLE
        fabDate.startAnimation(fabClose)
        fabDate.isClickable = false

        tvCamera.visibility = View.INVISIBLE
        fabCamera.startAnimation(fabClose)
        fabSetting.startAnimation(fabRotateAnticlock)
        fabCamera.isClickable = false
    } else {
        fabDate.startAnimation(fabOpen)
        fabDate.isClickable = true
        tvDate.visibility = View.VISIBLE

        fabCamera.startAnimation(fabOpen)
        fabSetting.startAnimation(fabRotateClock)
        fabCamera.isClickable = true
        tvCamera.visibility = View.VISIBLE
    }
}

fun FragmentGalleryBinding.chooseDate(
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
        changeFab(true, context)
    }, year, month, day)
    dpd.datePicker.maxDate = maxDate.time
    dpd.datePicker.minDate = minDate.time
    dpd.show()
    return newDate
}

fun FragmentGalleryBinding.chooseCamera(
    context: Context,
    cameras: Array<CharSequence>,
    adapter: GalleryAdapter
) {
    val checkedIndex = arrayListOf<Int>()
    val builder = AlertDialog.Builder(context)
    builder.setTitle(R.string.choose_a_camera)
    builder.setMultiChoiceItems(cameras, null) { _, which, isChecked ->
        if (isChecked) {
            checkedIndex.add(which)
        } else {
            checkedIndex.remove(Integer.valueOf(which))
        }
    }
        .setPositiveButton(R.string.ok_button) { _, id ->
            val chooseList = mutableListOf<String>()
            checkedIndex.forEach {
                chooseList.add(cameras[it].toString())
            }
            adapter.chooseCamera(chooseList)
            if (tvCamera.isVisible) {
                this.changeFab(true, context)
            }
        }
        .setNeutralButton(context.getText(R.string.select_all)) { _, which ->
            val chooseList = mutableListOf<String>()
            cameras.forEach { _ ->
                checkedIndex.add(which)
            }
            adapter.chooseCamera(chooseList)
            if (tvCamera.isVisible) {
                this.changeFab(true, context)
            }
        }
        .setNegativeButton(R.string.cancel_button) { _, _ ->
            if (tvCamera.isVisible) {
                this.changeFab(true, context)
            }
        }
        .create()
        .show()
}