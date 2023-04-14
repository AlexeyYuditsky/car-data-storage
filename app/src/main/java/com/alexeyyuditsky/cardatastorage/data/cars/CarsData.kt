package com.alexeyyuditsky.cardatastorage.data.cars

import com.alexeyyuditsky.cardatastorage.core.Abstract
import com.alexeyyuditsky.cardatastorage.data.cars.cache.CarDb
import com.alexeyyuditsky.cardatastorage.domain.cars.CarsDomain

sealed class CarsData : Abstract.DataToDomain<CarsDomain> {

    data class Success(
        private val cars: List<CarDb>,
    ) : CarsData() {
        override fun map(): CarsDomain {
            return CarsDomain.Success(cars)
        }
    }

    data class Fail(
        private val e: Exception,
    ) : CarsData() {
        override fun map(): CarsDomain {
            return CarsDomain.Fail(errorType(e))
        }
    }

    object Empty : CarsData() {
        override fun map(): CarsDomain {
            return CarsDomain.Empty
        }
    }

}
