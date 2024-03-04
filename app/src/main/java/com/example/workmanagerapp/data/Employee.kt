package com.example.workmanagerapp.data

import com.google.gson.annotations.SerializedName

data class Employee(
    @SerializedName("id") val id: Int,
    @SerializedName("employee_name") val employeeName: String,
    @SerializedName("employee_salary") val employeeSalary: Int,
    @SerializedName("employee_age") val employeeAge: Int,
    @SerializedName("profile_image") val profileImage: String
)

data class EmployeeData(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: List<Employee>,
    @SerializedName("message") val message: String
)
