package com.example.nychighschools.di

import android.app.Application

class SchoolsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        schoolsComponent = DaggerSchoolsComponent.builder().applicationModule(ApplicationModule(this)).build()
    }

    companion object {
        lateinit var schoolsComponent: SchoolsComponent
    }
}