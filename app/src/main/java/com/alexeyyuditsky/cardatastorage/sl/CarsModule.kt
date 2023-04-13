package com.alexeyyuditsky.cardatastorage.sl

import com.alexeyyuditsky.cardatastorage.presentation.CarsViewModel

class CarsModule(
    private val coreModule: CoreModule,
) : BaseModule<CarsViewModel> {

    override fun getViewModel(): CarsViewModel {
        return CarsViewModel(coreModule.database)
    }

}