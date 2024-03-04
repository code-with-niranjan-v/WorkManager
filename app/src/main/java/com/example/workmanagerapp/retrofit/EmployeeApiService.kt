package com.example.workmanagerapp.retrofit

import com.example.workmanagerapp.data.EmployeeData
import com.example.workmanagerapp.path.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface EmployeeApiService {

    @GET("employees")
    fun getAllEmployees():Call<EmployeeData>

}

val api by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EmployeeApiService::class.java)
}