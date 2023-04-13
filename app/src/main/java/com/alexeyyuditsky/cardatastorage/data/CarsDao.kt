package com.alexeyyuditsky.cardatastorage.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CarsDao {

    @Query("SELECT * FROM cars")
    suspend fun getAllCars(): List<CarDbEntity>

}
