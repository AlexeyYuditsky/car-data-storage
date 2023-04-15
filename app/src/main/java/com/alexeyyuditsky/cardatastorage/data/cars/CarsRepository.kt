package com.alexeyyuditsky.cardatastorage.data.cars

import com.alexeyyuditsky.cardatastorage.data.cars.cache.UpdateCarTuple
import com.alexeyyuditsky.cardatastorage.data.cars.cache.CarsCacheDataSource
import com.alexeyyuditsky.cardatastorage.data.cars.cache.NewCarTuple

interface CarsRepository {

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
            return try {
                val carDbList = cacheDataSource.fetchAllCars()
                if (carDbList.isEmpty()) {
                    CarsData.Empty
                } else {
                    CarsData.Success(carDbList)
                }
            } catch (e: Exception) {
                CarsData.Fail(e)
            }
        }

        override suspend fun fetchSortByBrandCars(): CarsData {
            return try {
                val carDbList = cacheDataSource.fetchSortByBrandCars()
                if (carDbList.isEmpty()) {
                    CarsData.Empty
                } else {
                    CarsData.Success(carDbList)
                }
            } catch (e: Exception) {
                CarsData.Fail(e)
            }
        }

        override suspend fun fetchFilterBySpeedCars(): CarsData {
            return try {
                val carDbList = cacheDataSource.fetchFilterBySpeedCars()
                if (carDbList.isEmpty()) {
                    CarsData.Empty
                } else {
                    CarsData.Success(carDbList)
                }
            } catch (e: Exception) {
                CarsData.Fail(e)
            }
        }

        override suspend fun fetchSortAndFilterByCars(): CarsData {
            return try {
                val carDbList = cacheDataSource.fetchSortAndFilterByCars()
                if (carDbList.isEmpty()) {
                    CarsData.Empty
                } else {
                    CarsData.Success(carDbList)
                }
            } catch (e: Exception) {
                CarsData.Fail(e)
            }
        }

        override suspend fun updateCar(carTuple: UpdateCarTuple) {
            cacheDataSource.updateCar(carTuple)
        }

        override suspend fun newCar(carTuple: NewCarTuple) {
            cacheDataSource.newCar(carTuple)
        }
    }

}