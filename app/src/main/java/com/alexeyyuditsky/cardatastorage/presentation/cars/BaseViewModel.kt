package com.alexeyyuditsky.cardatastorage.presentation.cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.cardatastorage.core.ResourceProvider
import com.alexeyyuditsky.cardatastorage.domain.cars.CarsDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel(
    private val resourceProvider: ResourceProvider,
) : ViewModel() {

    private val _carsLiveData = MutableLiveData<List<CarUi>>()
    val carsLiveData: LiveData<List<CarUi>> get() = _carsLiveData

    fun fetchCars(fetchCarsLamb: suspend () -> CarsDomain) = viewModelScope.launch {
        _carsLiveData.value = listOf(CarUi.Progress)
        withContext(Dispatchers.IO) {
            val carsDomain = fetchCarsLamb()
            val carsUi = carsDomain.map(resourceProvider)
            withContext(Dispatchers.Main) {
                _carsLiveData.value = carsUi.cars
            }
        }
    }

    fun saveCars(saveCarsLamb: suspend () -> Unit) = viewModelScope.launch(Dispatchers.IO) { saveCarsLamb() }

}