package com.alexeyyuditsky.cardatastorage.sl.cars

import com.alexeyyuditsky.cardatastorage.presentation.cars.CarsViewModel
import com.alexeyyuditsky.cardatastorage.sl.core.BaseFactory

class CarsFactory(
    carsModule: CarsModule,
) : BaseFactory<CarsViewModel>(carsModule)