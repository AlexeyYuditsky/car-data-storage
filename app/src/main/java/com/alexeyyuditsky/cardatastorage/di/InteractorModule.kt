package com.alexeyyuditsky.cardatastorage.di

import com.alexeyyuditsky.cardatastorage.domain.cars.CarsInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InteractorModule {

    @Binds
    @Singleton
    abstract fun bindCarsInteractor(
        carsInteractor: CarsInteractor.Base,
    ): CarsInteractor

}