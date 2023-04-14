package com.alexeyyuditsky.cardatastorage.domain.cars

import com.alexeyyuditsky.cardatastorage.core.Abstract
import com.alexeyyuditsky.cardatastorage.core.ErrorType
import com.alexeyyuditsky.cardatastorage.core.ResourceProvider
import com.alexeyyuditsky.cardatastorage.data.cars.cache.CarDb
import com.alexeyyuditsky.cardatastorage.presentation.cars.CarUi
import com.alexeyyuditsky.cardatastorage.presentation.cars.CarsUi

sealed class CarsDomain : Abstract.DomainToUi<CarsUi> {

    data class Success(
        private val cars: List<CarDb>,
    ) : CarsDomain() {

        override fun map(resourceProvider: ResourceProvider): CarsUi {
            val carUiList = cars.map { car ->
                CarUi.Base(car.id, car.model, car.color, car.speed, car.hp, null)
            }
            return CarsUi(carUiList)
        }

    }

    data class Fail(
        private val e: ErrorType,
    ) : CarsDomain() {

        override fun map(resourceProvider: ResourceProvider): CarsUi {
            val errorMessage = errorMessage(e, resourceProvider)
            return CarsUi(listOf(CarUi.Fail(errorMessage)))
        }

    }

    object Empty : CarsDomain() {

        override fun map(resourceProvider: ResourceProvider): CarsUi {
            return CarsUi(listOf(CarUi.Empty))
        }
    }

}
