package com.alexeyyuditsky.cardatastorage.domain.cars

import android.database.sqlite.SQLiteException
import com.alexeyyuditsky.cardatastorage.core.ErrorType
import com.alexeyyuditsky.cardatastorage.data.cars.CarsData
import org.junit.Assert.assertEquals
import org.junit.Test

class CarsInteractorTest {

    @Test
    fun test_success() {
        val data = CarsData.Success(listOf())

        val actual = data.map()
        val expected = CarsDomain.Success(listOf())

        assertEquals(expected, actual)
    }

    @Test
    fun test_fail() {
        val data = CarsData.Fail(SQLiteException())

        val actual = data.map()
        val expected = CarsDomain.Fail(ErrorType.NO_DATABASE)

        assertEquals(expected, actual)
    }

}