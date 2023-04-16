package com.alexeyyuditsky.cardatastorage.data.cars

import com.alexeyyuditsky.cardatastorage.data.cars.cache.CarsCacheDataSource
import com.alexeyyuditsky.cardatastorage.data.cars.cache.NewCarTuple
import com.alexeyyuditsky.cardatastorage.data.cars.cache.UpdateCarTuple

interface CarsRepository : BaseCarsRepository {

    suspend fun fetchAllCars(): CarsData
    suspend fun fetchSortByBrandCars(): CarsData
    suspend fun fetchFilterBySpeedCars(): CarsData
    suspend fun fetchSortAndFilterByCars(): CarsData
    suspend fun updateCar(carTuple: UpdateCarTuple)
    suspend fun newCar(carTuple: NewCarTuple)

    class Base(
        private val cacheDataSource: CarsCacheDataSource,
    ) : CarsRepository {

        override suspend fun fetchAllCars(): CarsData {
            return fetchCars { cacheDataSource.fetchAllCars() }
        }

        override suspend fun fetchSortByBrandCars(): CarsData {
            return fetchCars { cacheDataSource.fetchSortByBrandCars() }
        }

        override suspend fun fetchFilterBySpeedCars(): CarsData {
            return fetchCars { cacheDataSource.fetchFilterBySpeedCars() }
        }

        override suspend fun fetchSortAndFilterByCars(): CarsData {
            return fetchCars { cacheDataSource.fetchSortAndFilterByCars() }
        }

        override suspend fun updateCar(carTuple: UpdateCarTuple) {
            cacheDataSource.updateCar(carTuple)
        }

        override suspend fun newCar(carTuple: NewCarTuple) {
            cacheDataSource.newCar(carTuple)
        }

    }

}