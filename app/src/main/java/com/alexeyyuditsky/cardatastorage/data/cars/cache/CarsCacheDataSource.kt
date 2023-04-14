package com.alexeyyuditsky.cardatastorage.data.cars.cache

interface CarsCacheDataSource {

    suspend fun fetchAllCars(): List<CarDb>
    suspend fun fetchSortByBrandCars(): List<CarDb>
    suspend fun fetchFilterBySpeedCars(): List<CarDb>
    suspend fun fetchSortAndFilterByCars(): List<CarDb>

    class Base(
        private val room: AppDatabase,
    ) : CarsCacheDataSource {

        override suspend fun fetchAllCars(): List<CarDb> {
            return room.getCarsDao().getAllCars()
        }

        override suspend fun fetchSortByBrandCars(): List<CarDb> {
            return room.getCarsDao().getSortByBrandCars()
        }

        override suspend fun fetchFilterBySpeedCars(): List<CarDb> {
            return room.getCarsDao().getFilterBySpeedCars()
        }

        override suspend fun fetchSortAndFilterByCars(): List<CarDb> {
            return room.getCarsDao().getSortAndFilterByCars()
        }

    }

}