package com.alexeyyuditsky.cardatastorage.di

import android.content.Context
import androidx.room.Room
import com.alexeyyuditsky.cardatastorage.data.cars.cache.CarsDatabase
import com.alexeyyuditsky.cardatastorage.data.cars.cache.CarsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
    ): CarsDatabase {
        return Room
            .databaseBuilder(appContext, CarsDatabase::class.java, "cars.db")
            .createFromAsset("initial_database_cars.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideCarsDao(
        db: CarsDatabase,
    ): CarsDao {
        return db.getCarsDao()
    }

}