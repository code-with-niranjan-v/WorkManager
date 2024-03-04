package com.example.workmanagerapp.di

import android.content.Context
import com.example.workmanagerapp.db.EmployeeDatabase
import com.example.workmanagerapp.path.BASE_URL
import com.example.workmanagerapp.retrofit.EmployeeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {


    @Provides
    @Singleton
    fun provideDb(@ApplicationContext app:Context) = EmployeeDatabase(app)

    @Provides
    @Singleton
    fun provideRetrofit():EmployeeApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EmployeeApiService::class.java)

}