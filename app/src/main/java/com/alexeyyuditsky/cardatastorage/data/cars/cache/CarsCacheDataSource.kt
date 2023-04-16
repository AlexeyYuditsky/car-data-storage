package com.alexeyyuditsky.cardatastorage.data.cars.cache

interface CarsCacheDataSource {

    suspend fun fetchAllCars(): List<CarDb>
    suspend fun fetchSortByBrandCars(): List<CarDb>
    suspend fun fetchFilterBySpeedCars(): List<CarDb>
    suspend fun fetchSortAndFilterByCars(): List<CarDb>
    suspend fun updateCar(carTuple: UpdateCarTuple)
    suspend fun newCar(carTuple: NewCarTuple)

    class Base(
        private val carsDao: CarsDao,
    ) : CarsCacheDataSource {

        override suspend fun fetchAllCars(): List<CarDb> {
            return carsDao.getAllCars()
        }

        override suspend fun fetchSortByBrandCars(): List<CarDb> {
            return carsDao.getSortByBrandCars()
        }

        override suspend fun fetchFilterBySpeedCars(): List<CarDb> {
            return carsDao.getFilterBySpeedCars()
        }

        override suspend fun fetchSortAndFilterByCars(): List<CarDb> {
            return carsDao.getSortAndFilterByCars()
        }

        override suspend fun updateCar(carTuple: UpdateCarTuple) {
            carsDao.updateCar(carTuple)
        }

        override suspend fun newCar(carTuple: NewCarTuple) {
            carsDao.newCar(carTuple)
        }

    }

}