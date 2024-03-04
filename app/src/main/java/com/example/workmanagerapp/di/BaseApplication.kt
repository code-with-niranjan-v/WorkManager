package com.example.workmanagerapp.di

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.workmanagerapp.worker.MyCustomWorker
import com.example.workmanagerapp.db.EmployeeDatabase
import com.example.workmanagerapp.retrofit.EmployeeApiService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication:Application(),Configuration.Provider {

    @Inject lateinit var  workerFactory:CustomWorkerFactory
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()

}

class CustomWorkerFactory @Inject constructor(
    private val api:EmployeeApiService,
    private val db:EmployeeDatabase
):WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = MyCustomWorker(api = api, db = db, context = appContext, workerParameters = workerParameters)
}