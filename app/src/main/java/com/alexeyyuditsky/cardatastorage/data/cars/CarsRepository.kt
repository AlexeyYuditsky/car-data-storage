package com.alexeyyuditsky.cardatastorage.data.cars

import com.alexeyyuditsky.cardatastorage.data.cars.cache.CarsCacheDataSource

interface CarsRepository {

    suspend fun fetchAllCars(): CarsData
    suspend fun fetchSortByBrandCars(): CarsData
    suspend fun fetchFilterBySpeedCars(): CarsData
    suspend fun fetchSortAndFilterByCars(): CarsData

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

    }

}