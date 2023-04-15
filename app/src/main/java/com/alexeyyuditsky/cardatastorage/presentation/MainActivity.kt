package com.alexeyyuditsky.cardatastorage.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.alexeyyuditsky.cardatastorage.R
import com.alexeyyuditsky.cardatastorage.presentation.cars.CarUi
import com.alexeyyuditsky.cardatastorage.presentation.cars.EditCarDialogFragment
import com.alexeyyuditsky.cardatastorage.presentation.cars.CarsFragment
import com.alexeyyuditsky.cardatastorage.presentation.cars.NewCarDialogFragment

class MainActivity : AppCompatActivity(R.layout.activity_main), FragmentRouter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragmentContainer, CarsFragment())
            }
        }
    }

    override fun showEditCarDialog(car: CarUi) {
        EditCarDialogFragment
            .newInstance(car)
            .show(supportFragmentManager, null)
    }

    override fun showNewCarDialog() {
        NewCarDialogFragment
            .newInstance()
            .show(supportFragmentManager, null)
    }

}