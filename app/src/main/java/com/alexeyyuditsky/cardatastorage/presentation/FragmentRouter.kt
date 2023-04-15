package com.alexeyyuditsky.cardatastorage.presentation

import com.alexeyyuditsky.cardatastorage.presentation.cars.CarUi

interface FragmentRouter {

    fun showEditCarDialog(car: CarUi)

    fun showNewCarDialog()

    fun showFullscreenDialog(uri: String)

}