package com.example.nychighschools.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nychighschools.R
import com.example.nychighschools.data.models.SchoolInfoResponse
import com.example.nychighschools.databinding.FragmentSchoolInformationBinding
import com.example.nychighschools.utils.BaseFragment
import com.example.nychighschools.utils.UiState
import com.example.nychighschools.utils.ViewIntent
import com.example.nychighschools.view.adapter.SchoolInformationAdapter

class SchoolInformationFragment: BaseFragment() {
    private val binding by lazy {
        FragmentSchoolInformationBinding.inflate(layoutInflater)
    }

    private val schoolAdapter by lazy {
        SchoolInformationAdapter{ code, name ->
            schoolsViewModel.selectItem(code)
            findNavController().navigate(R.id.action_school_information_to_school_detail, Bundle().apply { putString("title", name) })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.rvSchoolsList.apply {
            adapter = schoolAdapter
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL,false)
        }
        // Inflate the layout for this fragment
        schoolsViewModel.schoolsInfo.observe(viewLifecycleOwner){ state ->
            when(state){
                is UiState.LOADING -> {}
                is UiState.SUCCESS<List<SchoolInfoResponse>> -> {
                    binding.progressBar.visibility = View.GONE
                    schoolAdapter.updateSchools(state.response)
                }
                is UiState.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    AlertDialog.Builder(requireActivity())
                        .setTitle("Error occured")
                        .setMessage(state.error.localizedMessage)
                        .setPositiveButton("Retry"){dialog,_ ->
                            schoolsViewModel.getSchools()
                            dialog.dismiss()
                        }
                        .setNegativeButton("Dismiss"){dialog,_ ->
                            dialog.dismiss()
                        }
                }
            }
        }

        schoolsViewModel.getIntentView(ViewIntent.SCHOOLS)

        return binding.root
    }
}