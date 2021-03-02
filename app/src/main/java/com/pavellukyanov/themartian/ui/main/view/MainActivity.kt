package com.pavellukyanov.themartian.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.ui.main.view.fragments.FragmentRoverDetails
import com.pavellukyanov.themartian.ui.main.view.fragments.FragmentTest
import com.pavellukyanov.themartian.ui.main.view.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.flMainFragmetn, FragmentTest())
                .commit()
        }
    }


}