package com.example.nychighschools.di

import com.example.nychighschools.data.SchoolsRepository
import com.example.nychighschools.data.SchoolsRepositoryImpl
import dagger.Binds
import dagger.Module

/**
 * [Abstract Class] - Module for repository
 */

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideSchoolsRepository(
        schoolsRepositoryImpl: SchoolsRepositoryImpl
    ): SchoolsRepository

}