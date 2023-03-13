package com.example.nychighschools.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nychighschools.data.SchoolsRepository
import com.example.nychighschools.data.models.SchoolInfoResponse
import com.example.nychighschools.utils.UiState
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SchoolsViewModelTest {

    private lateinit var testObject: SchoolsViewModel

    private val mockRepository = mockk<SchoolsRepository>(relaxed = true)
    private val mockDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTask =  InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mockDispatcher)
        testObject = SchoolsViewModel(mockRepository, mockDispatcher)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `get schools when repository retrieves a list of school response  returns success state`() {
        //  AAA
        // Arrange
        every { mockRepository.getAllSchools() } returns flowOf(
            UiState.SUCCESS(listOf(mockk(), mockk(), mockk()))
        )
        testObject.schoolsInfo.observeForever {
            when(it)  {
                is UiState.LOADING -> {

                }
                is UiState.SUCCESS -> {
                    Assert.assertEquals(3, it.response.size)
                }
                is UiState.ERROR -> {

                }
            }
        }
        val list =  mutableListOf<SchoolInfoResponse>()
        testObject.schoolsInfoList.observeForever {
            list.addAll(it!!)
        }

        // Act
        testObject.getSchools()

        // Assert
        Assert.assertEquals(3, list.size)
    }
}