package com.pavellukyanov.themartian.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.ui.main.adapter.MainAdapter
import com.pavellukyanov.themartian.ui.main.view.fragments.PhotoGalleryFragment
import com.pavellukyanov.themartian.ui.main.viewmodel.MainVewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.flMainFragmetn, PhotoGalleryFragment())
                .commit()
        }
    }


}