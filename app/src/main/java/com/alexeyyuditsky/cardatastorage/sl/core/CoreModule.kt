package com.alexeyyuditsky.cardatastorage.sl.core

import android.content.Context
import androidx.room.Room
import com.alexeyyuditsky.cardatastorage.core.ResourceProvider
import com.alexeyyuditsky.cardatastorage.data.cars.cache.AppDatabase

object CoreModule {

    lateinit var resourceProvider: ResourceProvider
    lateinit var database: AppDatabase

    fun init(context: Context) {
        resourceProvider = ResourceProvider.Base(context)

        database = Room
            .databaseBuilder(context, AppDatabase::class.java, "cars.db")
            .createFromAsset("initial_database_cars.db")
            .build()
    }

}