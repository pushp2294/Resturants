package com.pushp.resturants.di

import android.app.Application
import androidx.room.Room
import com.pushp.resturants.room.ResturantDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestaurantModule {
    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): ResturantDatabase {
        return Room
            .databaseBuilder(app, ResturantDatabase::class.java, "Resturant_Database")
            .fallbackToDestructiveMigration()
            .build()
    }
}