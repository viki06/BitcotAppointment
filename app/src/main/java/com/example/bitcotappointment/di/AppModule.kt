package com.example.bitcotappointment.di

import android.app.Application
import com.example.bitcotappointment.pref.AppPreferences
import com.example.bitcotappointment.repository.Repository
import com.example.bitcotappointment.room.DbController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppPreferences(application: Application): AppPreferences =
        AppPreferences(context = application)

    @Provides
    @Singleton
    fun provideUserRepository(dbController: DbController) : Repository = Repository(dbController)

    @Provides
    @Singleton
    fun provideDbController(application: Application) : DbController = DbController(application)

}