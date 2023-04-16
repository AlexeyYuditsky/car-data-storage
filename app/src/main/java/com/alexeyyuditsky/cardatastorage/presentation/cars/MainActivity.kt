package com.alexeyyuditsky.cardatastorage.presentation.cars

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.alexeyyuditsky.cardatastorage.R

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

    override fun showFullscreenDialog(uri: String) {
        FullscreenDialogFragment
            .newInstance(uri)
            .show(supportFragmentManager, null)
    }

}