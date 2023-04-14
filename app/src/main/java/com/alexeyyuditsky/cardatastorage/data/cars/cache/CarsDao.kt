package com.alexeyyuditsky.cardatastorage.data.cars.cache

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CarsDao {

    @Query("SELECT * FROM cars")
    suspend fun getAllCars(): List<CarDb>

    @Query("SELECT * FROM cars ORDER BY model ASC")
    suspend fun getSortByBrandCars(): List<CarDb>

    @Query("SELECT * FROM cars WHERE speed > 189 ORDER BY speed DESC")
    suspend fun getFilterBySpeedCars(): List<CarDb>

    @Query("SELECT * FROM cars WHERE speed > 189 ORDER BY model DESC")
    suspend fun getSortAndFilterByCars(): List<CarDb>

}
