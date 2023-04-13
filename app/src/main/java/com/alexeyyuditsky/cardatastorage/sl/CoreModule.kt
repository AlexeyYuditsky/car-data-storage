package com.alexeyyuditsky.cardatastorage.sl

import android.content.Context
import androidx.room.Room
import com.alexeyyuditsky.cardatastorage.data.AppDatabase

object CoreModule {

    lateinit var database: AppDatabase

    fun init(context: Context) {
        database = Room.databaseBuilder(context, AppDatabase::class.java, "cars.db")
            .createFromAsset("initial_database_cars.db")
            .build()
    }

}