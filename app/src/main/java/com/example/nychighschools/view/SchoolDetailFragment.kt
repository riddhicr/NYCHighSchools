package com.example.nychighschools.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nychighschools.data.models.SatResultsResponse
import com.example.nychighschools.data.models.SchoolInfoResponse
import com.example.nychighschools.databinding.FragmentSchoolDetailsBinding
import com.example.nychighschools.utils.BaseFragment
import com.example.nychighschools.utils.ViewIntent

class SchoolDetailFragment:BaseFragment() {
    private val binding by lazy{
        FragmentSchoolDetailsBinding.inflate(layoutInflater)
    }

    private var schoolItem: SchoolInfoResponse = SchoolInfoResponse()
    private var satItem: SatResultsResponse = SatResultsResponse()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        schoolsViewModel.schoolsInfoList.observe(viewLifecycleOwner){
            getSchoolItem(schoolsViewModel.itemSelected.value.toString(),it)
            binding.tvSchoolName.text = schoolItem.schoolName ?: ""
            binding.tvOverviewParagraph.text = schoolItem.overviewParagraph ?: ""
            binding.btnFindMe.setOnClickListener {
                val gmmUri = Uri.parse(
                    "geo:${schoolItem.latitude},${schoolItem.longitude}?z=20")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }
        schoolsViewModel.satResultsList.observe(viewLifecycleOwner){
            getSatItem(schoolsViewModel.itemSelected.value.toString(),it)
            binding.tvMathScore.text = satItem.satMathAvgScore ?: "NA"
            binding.tvReadingScore.text = satItem.satCriticalReadingAvgScore ?: "NA"
            binding.tvWritingScore.text = satItem.satWritingAvgScore ?: "NA"
        }
        schoolsViewModel.getIntentView(ViewIntent.SatSCORES)

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getSchoolItem(dbn: String, schoolList: List<SchoolInfoResponse>?){
        var found = false
        var i = 0
        while (!found && i < (schoolList?.size ?: 0)){
            if(schoolList?.get(i)?.dbn == dbn){
                schoolItem = schoolList[i]
                found = true
            }
            i++
        }
    }

    private fun getSatItem(dbn: String, satList: List<SatResultsResponse>?){
        var found = false
        var i = 0
        while (!found && i < (satList?.size ?: 0)){
            if(satList?.get(i)?.dbn == dbn){
                satItem = satList[i]
                found = true
            }
            i++
        }
    }
}