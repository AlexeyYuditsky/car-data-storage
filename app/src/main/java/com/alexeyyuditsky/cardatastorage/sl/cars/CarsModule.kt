package com.alexeyyuditsky.cardatastorage.sl.cars

import com.alexeyyuditsky.cardatastorage.data.cars.CarsRepository
import com.alexeyyuditsky.cardatastorage.data.cars.cache.CarsCacheDataSource
import com.alexeyyuditsky.cardatastorage.domain.cars.CarsInteractor
import com.alexeyyuditsky.cardatastorage.presentation.cars.CarsViewModel
import com.alexeyyuditsky.cardatastorage.sl.core.BaseModule
import com.alexeyyuditsky.cardatastorage.sl.core.CoreModule

class CarsModule(
    private val coreModule: CoreModule,
) : BaseModule<CarsViewModel> {

    override fun getViewModel(): CarsViewModel {
        return CarsViewModel(getCarsInteractor(), coreModule.resourceProvider)
    }

    private fun getCarsInteractor(): CarsInteractor {
        return CarsInteractor.Base(getCarsRepository())
    }

    private fun getCarsRepository(): CarsRepository {
        return CarsRepository.Base(getCarsCacheDataSource())
    }

    private fun getCarsCacheDataSource(): CarsCacheDataSource {
        return CarsCacheDataSource.Base(coreModule.database)
    }

}