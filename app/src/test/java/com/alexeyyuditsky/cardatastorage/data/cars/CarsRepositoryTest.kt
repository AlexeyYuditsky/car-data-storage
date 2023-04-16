package com.alexeyyuditsky.cardatastorage.data.cars

import android.database.sqlite.SQLiteException
import com.alexeyyuditsky.cardatastorage.data.cars.cache.CarDb
import com.alexeyyuditsky.cardatastorage.data.cars.cache.CarsCacheDataSource
import com.alexeyyuditsky.cardatastorage.data.cars.cache.NewCarTuple
import com.alexeyyuditsky.cardatastorage.data.cars.cache.UpdateCarTuple
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class CarsRepositoryTest {

    private val sqliteException = SQLiteException()

    @Test
    fun test_ok_database() = runBlocking {
        val cacheDataSourceTest = CarsCacheDataSourceTest(true)
        val repository = CarsRepository.Base(cacheDataSourceTest)

        val actual = repository.fetchAllCars()
        val expected = CarsData.Success(
            listOf(
                CarDb(0, "title0", "color0", 0, 0, "image0"),
                CarDb(1, "title1", "color1", 1, 1, "image1")
            )
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun test_fail_database() = runBlocking {
        val cacheDataSourceTest = CarsCacheDataSourceTest(false)
        val repository = CarsRepository.Base(cacheDataSourceTest)

        val actual = repository.fetchAllCars()
        val expected = CarsData.Fail(sqliteException)

        Assert.assertEquals(expected, actual)
    }

    private inner class CarsCacheDataSourceTest(
        private val returnSuccess: Boolean,
    ) : CarsCacheDataSource {

        override suspend fun fetchAllCars(): List<CarDb> {
            if (returnSuccess) {
                return listOf(
                    CarDb(0, "title0", "color0", 0, 0, "image0"),
                    CarDb(1, "title1", "color1", 1, 1, "image1")
                )
            } else {
                throw sqliteException
            }
        }

        override suspend fun fetchSortByBrandCars(): List<CarDb> {
            if (returnSuccess) {
                return listOf(
                    CarDb(0, "title0", "color0", 0, 0, "image0"),
                    CarDb(1, "title1", "color1", 1, 1, "image1")
                )
            } else {
                throw sqliteException
            }
        }

        override suspend fun fetchFilterBySpeedCars(): List<CarDb> {
            if (returnSuccess) {
                return listOf(
                    CarDb(0, "title0", "color0", 0, 0, "image0"),
                    CarDb(1, "title1", "color1", 1, 1, "image1")
                )
            } else {
                throw sqliteException
            }
        }

        override suspend fun fetchSortAndFilterByCars(): List<CarDb> {
            if (returnSuccess) {
                return listOf(
                    CarDb(0, "title0", "color0", 0, 0, "image0"),
                    CarDb(1, "title1", "color1", 1, 1, "image1")
                )
            } else {
                throw sqliteException
            }
        }

        override suspend fun updateCar(carTuple: UpdateCarTuple) {
            // not required for the test
        }

        override suspend fun newCar(carTuple: NewCarTuple) {
            // not required for the test
        }

    }

}