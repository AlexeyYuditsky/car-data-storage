package com.alexeyyuditsky.cardatastorage.di

import com.alexeyyuditsky.cardatastorage.data.cars.CarsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCarsRepository(
        carsRepository: CarsRepository.Base,
    ): CarsRepository

}