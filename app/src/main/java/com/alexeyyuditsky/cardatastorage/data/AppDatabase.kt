package com.alexeyyuditsky.cardatastorage.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [CarDbEntity::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCarsDao(): CarsDao

}