package com.alexeyyuditsky

import android.app.Application
import com.alexeyyuditsky.cardatastorage.sl.CarsFactory
import com.alexeyyuditsky.cardatastorage.sl.CarsModule
import com.alexeyyuditsky.cardatastorage.sl.CoreModule

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