package com.alexeyyuditsky.cardatastorage.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.cardatastorage.data.AppDatabase
import com.alexeyyuditsky.cardatastorage.data.CarDbEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CarsViewModel(
    private val database: AppDatabase,
) : ViewModel() {

    private val _carsLiveData = MutableLiveData<List<CarDbEntity>>()
    val carsLiveData: LiveData<List<CarDbEntity>> get() = _carsLiveData

    init {
        fetchAllCars()
    }

    private fun fetchAllCars() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val cars = database.getCarsDao().getAllCars()
            withContext(Dispatchers.Main) {
                _carsLiveData.value = cars
            }
        }
    }

}