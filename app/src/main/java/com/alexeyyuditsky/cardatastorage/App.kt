package com.alexeyyuditsky.cardatastorage

import android.app.Application
import com.alexeyyuditsky.cardatastorage.sl.cars.CarsFactory
import com.alexeyyuditsky.cardatastorage.sl.cars.CarsModule
import com.alexeyyuditsky.cardatastorage.sl.core.CoreModule

class App : Application() {

    private val coreModule = CoreModule

    override fun onCreate() {
        super.onCreate()
        coreModule.init(this)
    }

    fun carsFactory(): CarsFactory {
        return CarsFactory(CarsModule(coreModule))
    }

}