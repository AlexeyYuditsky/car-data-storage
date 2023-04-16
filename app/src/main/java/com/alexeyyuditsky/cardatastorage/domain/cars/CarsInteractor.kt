package com.alexeyyuditsky.cardatastorage.domain.cars

import com.alexeyyuditsky.cardatastorage.data.cars.CarsRepository
import com.alexeyyuditsky.cardatastorage.data.cars.cache.NewCarTuple
import com.alexeyyuditsky.cardatastorage.data.cars.cache.UpdateCarTuple

interface CarsInteractor {

    suspend fun fetchAllCars(): CarsDomain
    suspend fun fetchSortByBrandCars(): CarsDomain
    suspend fun fetchFilterBySpeedCars(): CarsDomain
    suspend fun fetchSortAndFilterByCars(): CarsDomain
    suspend fun updateCar(carTuple: UpdateCarTuple)
    suspend fun newCar(carTuple: NewCarTuple)

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

        override suspend fun updateCar(carTuple: UpdateCarTuple) {
            repository.updateCar(carTuple)
        }

        override suspend fun newCar(carTuple: NewCarTuple) {
            repository.newCar(carTuple)
        }

    }

}