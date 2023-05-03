package com.alexeyyuditsky.cardatastorage.di

import com.alexeyyuditsky.cardatastorage.core.ResourceProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StuffsModule {

    @Binds
    @Singleton
    abstract fun bindResourceProvider(
        resourceProvider: ResourceProvider.Base,
    ): ResourceProvider

}