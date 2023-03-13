package com.example.nychighschools.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.nychighschools.data.SchoolsRepository
import com.example.nychighschools.utils.SchoolsViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    fun provideContext(): Context = application.applicationContext

    /**
     * Methods that provides View Model Factory
     */
    @Provides
    fun provideViewModelFactory(
        repository: SchoolsRepository,
        ioDispatcher: CoroutineDispatcher
    ): SchoolsViewModelFactory =
        SchoolsViewModelFactory(repository, ioDispatcher)

}