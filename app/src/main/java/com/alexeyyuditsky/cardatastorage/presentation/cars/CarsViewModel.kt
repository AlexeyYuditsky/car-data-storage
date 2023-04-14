package com.alexeyyuditsky.cardatastorage.presentation.cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.cardatastorage.core.ResourceProvider
import com.alexeyyuditsky.cardatastorage.domain.cars.CarsInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CarsViewModel(
    private val interactor: CarsInteractor,
    private val resourceProvider: ResourceProvider,
) : ViewModel() {

    private val _carsLiveData = MutableLiveData<List<CarUi>>(listOf(CarUi.Progress))
    val carsLiveData: LiveData<List<CarUi>> get() = _carsLiveData

    init {
        fetchAllCars()
    }

    fun fetchAllCars() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val carsDomain = interactor.fetchAllCars()
            val carsUi = carsDomain.map(resourceProvider)
            withContext(Dispatchers.Main) {
                _carsLiveData.value = carsUi.cars
            }
        }
    }

    fun fetchSortByBrandCars() = viewModelScope.launch {
        _carsLiveData.value = listOf(CarUi.Progress)
        withContext(Dispatchers.IO) {
            val carsDomain = interactor.fetchSortByBrandCars()
            val carsUi = carsDomain.map(resourceProvider)
            withContext(Dispatchers.Main) {
                _carsLiveData.value = carsUi.cars
            }
        }
    }

    fun fetchFilterBySpeedCars() = viewModelScope.launch {
        _carsLiveData.value = listOf(CarUi.Progress)
        withContext(Dispatchers.IO) {
            val carsDomain = interactor.fetchFilterBySpeedCars()
            val carsUi = carsDomain.map(resourceProvider)
            withContext(Dispatchers.Main) {
                _carsLiveData.value = carsUi.cars
            }
        }
    }

    fun fetchSortAndFilterByCars() = viewModelScope.launch {
        _carsLiveData.value = listOf(CarUi.Progress)
        withContext(Dispatchers.IO) {
            val carsDomain = interactor.fetchSortAndFilterByCars()
            val carsUi = carsDomain.map(resourceProvider)
            withContext(Dispatchers.Main) {
                _carsLiveData.value = carsUi.cars
            }
        }
    }

}