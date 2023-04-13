package com.alexeyyuditsky.cardatastorage.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexeyyuditsky.cardatastorage.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, CarsFragment())
                .commit()
        }
    }

}