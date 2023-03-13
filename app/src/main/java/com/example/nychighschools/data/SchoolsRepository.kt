package com.example.nychighschools.data

import android.util.Log
import com.example.nychighschools.data.models.SatResultsResponse
import com.example.nychighschools.data.models.SchoolInfoResponse
import com.example.nychighschools.utils.FailureResponseException
import com.example.nychighschools.utils.NullSatResultsException
import com.example.nychighschools.utils.NullSchoolsException
import com.example.nychighschools.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface SchoolsRepository {

    /**
     * Method to get schools
     */
    fun getAllSchools(): Flow<UiState<List<SchoolInfoResponse>>>

    /**
     * Method to get SAT results
     */
    fun getAllSatResults(): Flow<UiState<List<SatResultsResponse>>>

}

private const val TAG = "SchoolsRepository"

/**
 * [Class] - Implement retrofit
 */

class SchoolsRepositoryImpl @Inject constructor(
    private val schoolsAPI: SchoolsApi
) : SchoolsRepository{
    
    
    /**
     * Method to get schools
     */
    override fun getAllSchools(): Flow<UiState<List<SchoolInfoResponse>>> = flow{
        emit(UiState.LOADING)
        try {
            val response = schoolsAPI.getSchools() //get json
            if(response.isSuccessful) { //check if response was successful
                response.body()?.let {
                    Log.d(TAG, "getAllSchools: $it")
                    emit(UiState.SUCCESS(it))
                }?: throw NullSchoolsException() //check if response was null
            }else throw FailureResponseException(response.errorBody()?.string())
        }catch (e: Exception){
            emit(UiState.ERROR(e))
            Log.e(TAG, "getAllSchools: ${e.localizedMessage}", e)
        }
    }

    /**
     * Method to get SAT results
     */
    override fun getAllSatResults(): Flow<UiState<List<SatResultsResponse>>> = flow{
        emit(UiState.LOADING)
        try {
            val response = schoolsAPI.getSatResults() //get json
            if (response.isSuccessful){
                response.body()?.let {
                    emit(UiState.SUCCESS(it))
                }?: throw NullSatResultsException()
            } else{
                throw FailureResponseException(response.errorBody()?.string())
            }
        }catch (e: Exception){
            emit(UiState.ERROR(e))
            Log.e(TAG, "getAllSatResults: ${e.localizedMessage}", e)
        }
    }

}