package com.alexeyyuditsky.cardatastorage.presentation.cars.screens.main

import com.alexeyyuditsky.cardatastorage.core.ResourceProvider
import com.alexeyyuditsky.cardatastorage.data.cars.cache.NewCarTuple
import com.alexeyyuditsky.cardatastorage.data.cars.cache.UpdateCarTuple
import com.alexeyyuditsky.cardatastorage.domain.cars.CarsInteractor
import com.alexeyyuditsky.cardatastorage.presentation.cars.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CarsViewModel @Inject constructor(
    private val interactor: CarsInteractor,
    resourceProvider: ResourceProvider,
) : BaseViewModel(resourceProvider) {

    init {
        fetchAllCars()
    }

    fun fetchAllCars() {
        fetchCars { interactor.fetchAllCars() }
    }

    fun fetchSortByBrandCars() {
        fetchCars { interactor.fetchSortByBrandCars() }
    }

    fun fetchFilterBySpeedCars() {
        fetchCars { interactor.fetchFilterBySpeedCars() }
    }

    fun fetchSortAndFilterByCars() {
        fetchCars { interactor.fetchSortAndFilterByCars() }
    }

    fun updateCar(id: Long, model: String, color: String, speed: Int, hp: Int, image: String) {
        saveCars { interactor.updateCar(UpdateCarTuple(id, model, color, speed, hp, image)) }
    }

    fun addNewCar(model: String, color: String, speed: Int, hp: Int, image: String) {
        saveCars { interactor.newCar(NewCarTuple(0, model, color, speed, hp, image)) }
    }

}