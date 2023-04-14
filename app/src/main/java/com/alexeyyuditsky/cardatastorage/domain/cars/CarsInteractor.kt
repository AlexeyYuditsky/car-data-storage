package com.alexeyyuditsky.cardatastorage.domain.cars

import com.alexeyyuditsky.cardatastorage.data.cars.CarsRepository

interface CarsInteractor {

    suspend fun fetchAllCars(): CarsDomain
    suspend fun fetchSortByBrandCars(): CarsDomain
    suspend fun fetchFilterBySpeedCars(): CarsDomain
    suspend fun fetchSortAndFilterByCars(): CarsDomain

    class Base(
        private val repository: CarsRepository,
    ) : CarsInteractor {
        override suspend fun fetchAllCars(): CarsDomain {
            val carsData = repository.fetchAllCars()
            return carsData.map()
        }

        override suspend fun fetchSortByBrandCars(): CarsDomain {
            val carsData = repository.fetchSortByBrandCars()
            return carsData.map()
        }

        override suspend fun fetchFilterBySpeedCars(): CarsDomain {
            val carsData = repository.fetchFilterBySpeedCars()
            return carsData.map()
        }

        override suspend fun fetchSortAndFilterByCars(): CarsDomain {
            val carsData = repository.fetchSortAndFilterByCars()
            return carsData.map()
        }

    }

}