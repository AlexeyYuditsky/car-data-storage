package com.alexeyyuditsky.cardatastorage.data.cars

import com.alexeyyuditsky.cardatastorage.data.cars.cache.CarDb

interface BaseCarsRepository {

    suspend fun fetchCars(fetchCarsLamb: suspend () -> List<CarDb>): CarsData {
        return try {
            val carDbList = fetchCarsLamb()
            if (carDbList.isEmpty()) {
                CarsData.Empty
            } else {
                CarsData.Success(carDbList)
            }
        } catch (e: Exception) {
            CarsData.Fail(e)
        }
    }

}