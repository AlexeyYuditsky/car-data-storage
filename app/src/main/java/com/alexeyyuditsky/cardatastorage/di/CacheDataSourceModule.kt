package com.alexeyyuditsky.cardatastorage.di

import com.alexeyyuditsky.cardatastorage.data.cars.cache.CarsCacheDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindCarsCacheDataSource(
        dataSource: CarsCacheDataSource.Base,
    ): CarsCacheDataSource

}