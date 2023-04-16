package com.alexeyyuditsky.cardatastorage.presentation.cars

interface FragmentRouter {

    fun showEditCarDialog(car: CarUi)

    fun showNewCarDialog()

    fun showFullscreenDialog(uri: String)

}