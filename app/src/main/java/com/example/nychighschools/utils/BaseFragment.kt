package com.example.nychighschools.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nychighschools.di.SchoolsApplication
import com.example.nychighschools.viewmodel.SchoolsViewModel
import javax.inject.Inject

open class BaseFragment: Fragment() {

    @Inject
    lateinit var schoolsViewModelFactory: SchoolsViewModelFactory

    protected val schoolsViewModel: SchoolsViewModel by lazy {
        ViewModelProvider(requireActivity(), schoolsViewModelFactory)[SchoolsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SchoolsApplication.schoolsComponent.inject(this)
    }
}