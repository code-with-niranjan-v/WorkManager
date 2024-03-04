package com.example.workmanagerapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "EmployeeDb")
data class EmployeeDb(
    val employeeName: String,
    val employeeSalary: Int,
    val employeeAge: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)