package com.example.nychighschools.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nychighschools.data.SchoolsRepository
import com.example.nychighschools.data.models.SatResultsResponse
import com.example.nychighschools.data.models.SchoolInfoResponse
import com.example.nychighschools.utils.UiState
import com.example.nychighschools.utils.ViewIntent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class SchoolsViewModel(
    private val schoolsRepository: SchoolsRepository, private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    //backing schools LiveData
    private val _schoolsInfo: MutableLiveData<UiState<List<SchoolInfoResponse>>> = MutableLiveData(UiState.LOADING)
    val schoolsInfo: LiveData<UiState<List<SchoolInfoResponse>>> get() = _schoolsInfo

    //backing sat results LiveData
    private val _satResults: MutableLiveData<UiState<List<SatResultsResponse>>> = MutableLiveData(UiState.LOADING)
    val satResults: LiveData<UiState<List<SatResultsResponse>>> get() = _satResults

    private val _itemSelected: MutableLiveData<String> = MutableLiveData("")
    val itemSelected: LiveData<String> get() = _itemSelected

    //backing schools LiveData
    private val _schoolsInfoList: MutableLiveData<List<SchoolInfoResponse>?> = MutableLiveData()
    val schoolsInfoList: LiveData<List<SchoolInfoResponse>?> get() = _schoolsInfoList

    //backing sat results LiveData
    private val _satResultsList: MutableLiveData<List<SatResultsResponse>?> = MutableLiveData()
    val satResultsList: LiveData<List<SatResultsResponse>?> get() = _satResultsList


    fun getIntentView(intentView: ViewIntent) {
        when (intentView) {
            ViewIntent.SatSCORES -> {
                getSatResults()
            }
            ViewIntent.SCHOOLS -> {
                getSchools()
            }
            is ViewIntent.SchoolSCORE -> {
                //
            }
        }
    }


    /**
     * Method for getting the SAT Results List
     */
    private fun getSatResults() {
        viewModelScope.launch(ioDispatcher) {
            schoolsRepository.getAllSatResults().collect {
                _satResults.postValue(it)
                if (it is UiState.SUCCESS<List<SatResultsResponse>>) {
                    _satResultsList.postValue(it.response)
                }
            }
        }
    }

    /**
     * Method for getting the Schools List
     */
    fun getSchools() {
        viewModelScope.launch(ioDispatcher) {
            schoolsRepository.getAllSchools().collect {
                _schoolsInfo.postValue(it)
                if (it is UiState.SUCCESS<List<SchoolInfoResponse>>) {
                    _schoolsInfoList.postValue(it.response)
                }
            }
        }
    }

    fun selectItem(dbn: String) {
        _itemSelected.value = dbn
    }

}