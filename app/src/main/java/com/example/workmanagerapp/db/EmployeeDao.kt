package com.example.workmanagerapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workmanagerapp.data.EmployeeDb

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployee(data:EmployeeDb)

    @Query("select * from EmployeeDb")
    fun getEmployee():LiveData<List<EmployeeDb>>

}