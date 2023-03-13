package com.example.nychighschools.di

import com.example.nychighschools.MainActivity
import com.example.nychighschools.utils.BaseFragment
import dagger.Component

@Component(
    modules = [ApplicationModule::class, NetworkModule::class, RepositoryModule::class]
)
interface SchoolsComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(baseFragment: BaseFragment)
}