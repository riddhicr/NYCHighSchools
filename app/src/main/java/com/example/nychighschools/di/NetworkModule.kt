package com.example.nychighschools.di

import com.example.nychighschools.data.SchoolsApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * [Class] - Module for Network connection
 */

@Module
class NetworkModule {

    @Provides
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    /**
     * Method that provides retrofit connection for Repository
     */
    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SchoolsApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    /**
     * Method that provides OkHttpClient for Retrofit
     */
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Method that provides HttpLoggingInterceptor for OkHttpClient
     */
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    /**
     * Method that provides SchoolsAPi using retrofit
     */
    @Provides
    fun provideSchoolsApi(retrofit: Retrofit): SchoolsApi{
        return retrofit.create(SchoolsApi::class.java)
    }

    /**
     * Method that provides a IO Dispatcher for the network coroutine
     */
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}