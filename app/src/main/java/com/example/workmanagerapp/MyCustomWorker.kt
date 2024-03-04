package com.example.workmanagerapp

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanagerapp.data.EmployeeData
import com.example.workmanagerapp.data.EmployeeDb
import com.example.workmanagerapp.db.EmployeeDatabase
import com.example.workmanagerapp.retrofit.api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class MyCustomWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters,
):Worker(context,workerParameters) {
    override fun doWork(): Result {
        try {
            val call: Call<EmployeeData> = api.getAllEmployees()

            call.enqueue(object : Callback<EmployeeData> {
                override fun onResponse(call: Call<EmployeeData>, response: Response<EmployeeData>) {
                    if (response.isSuccessful) {
                        val employeeData = response.body()
                        Log.e("MyWork",employeeData.toString())
                    } else {
                        Log.e("MyWork","Failed")
                    }
                }

                override fun onFailure(call: Call<EmployeeData>, t: Throwable) {
                     Log.e("MyWork",t.message.toString())
                }
            })
            return Result.success()
        } catch (e: Exception) {
            Log.e("MyWork", "Work failed: ${e.message}")
            return Result.failure()
        }

    }
}